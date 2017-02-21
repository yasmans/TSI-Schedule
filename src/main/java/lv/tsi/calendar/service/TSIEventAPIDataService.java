package lv.tsi.calendar.service;

import lv.tsi.calendar.config.CacheConfiguration;
import lv.tsi.calendar.domain.Event;
import lv.tsi.calendar.domain.ReferenceData;
import lv.tsi.calendar.dto.EventsDTO;
import lv.tsi.calendar.service.search.SearchBean;
import lv.tsi.calendar.service.search.SearchQueryProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TSIEventAPIDataService implements DataService {

    public static final String URL_PARAM_LANG = "lang";
    public static final String URL_PARAM_TYPE = "type";
    public static final String URL_PARAM_DATE_FROM = "from";
    public static final String URL_PARAM_DATE_TO = "to";
    public static final String PARAM_TEACHERS = "teachers";
    public static final String PARAM_ROOMS = "rooms";
    public static final String PARAM_GROUPS = "groups";
    public static final String DELIMITER = " ";
    public static final String EVENTS = "events";
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_COMMENT = "comment";
    public static final String EVENT_TYPE = "type";
    public static final String EVENT_TIME = "time";
    public static final String EVENT_TEACHER_ID = "teacher";
    public static final String EVENT_ROOM_IDS = "room";
    public static final String EVENT_GROUP_IDS = "group";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMPTY_STRING = "";

    private String referenceDataURL;
    private String eventURL;
    private RestTemplate restTemplate = new RestTemplate();
    private JacksonJsonParser jsonParser = new JacksonJsonParser();
    private SearchQueryProcessor searchQueryProcessor;
    private ApplicationTimeService applicationTimeService;

    @Cacheable(CacheConfiguration.REFERENCE_DATA_CACHE)
    public Map<String, List<ReferenceData>> getReferenceData(String lang, String[] types) {
        Map<String, String> params = new HashMap<>();
        params.put(URL_PARAM_LANG, lang);
        params.put(URL_PARAM_TYPE, StringUtils.arrayToCommaDelimitedString(types));

        String responseBody = restTemplate.getForObject(referenceDataURL, String.class, params);
        String json = convertJsonPToJsonString(responseBody);
        if (json.isEmpty()) {
            return new HashMap<>();
        } else {
            return parseReferenceDataJson(json);
        }
    }

    protected Map<String, List<ReferenceData>> parseReferenceDataJson(String json) {
        Map<String, Object> typeToReferenceDataListMap = jsonParser.parseMap(json);
        Map<String, List<ReferenceData>> result = new HashMap<>();
        for (String key : typeToReferenceDataListMap.keySet()) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> referenceDataMapList = (List<Map<String, Object>>)  typeToReferenceDataListMap.get(key);
            List<ReferenceData> referenceDataList = referenceDataMapList.stream()
                    .map(map -> new ReferenceData((Integer) map.get(ID), (String) map.get(NAME)))
                    .collect(Collectors.toList());
            result.put(key, referenceDataList);
        }
        return result;
    }

    @Override
    public EventsDTO searchEvents(String searchQuery, String lang) {
        EventsDTO eventsDTO = new EventsDTO();

        SearchBean searchBean = searchQueryProcessor.createSearchBean(searchQuery);
        eventsDTO.setSearchBean(searchBean);

        // Prepare query for fetching data
        Map<String, List<ReferenceData>> referenceData = getReferenceData("en", new String[]{PARAM_GROUPS, PARAM_ROOMS, PARAM_TEACHERS});

        Map<String, String> params = new HashMap<>();
        params.put(URL_PARAM_DATE_FROM, String.valueOf(applicationTimeService.getStartOfPreviousMonth()));
        params.put(URL_PARAM_DATE_TO, String.valueOf(applicationTimeService.getStartOfMonthAfterHalfAYear()));
        params.put(URL_PARAM_LANG, lang);
        params.put(PARAM_GROUPS, getSearchTermIDs(searchBean.getGroupSearchTerms(), referenceData.get(PARAM_GROUPS)));
        params.put(PARAM_ROOMS, getSearchTermIDs(searchBean.getRoomSearchTerms(), referenceData.get(PARAM_ROOMS)));
        params.put(PARAM_TEACHERS, getSearchTermIDs(searchBean.getTeacherSearchTerms(), referenceData.get(PARAM_TEACHERS)));

        // Query for data
        String responseBody = restTemplate.getForObject(eventURL, String.class, params);
        String json = convertJsonPToJsonString(responseBody);
        List<Event> events = new ArrayList<>();
        if (!json.isEmpty()) {
            events.addAll(parseEventsJson(json));
        }
        eventsDTO.setEvents(filterEvents(events, searchBean));
        return eventsDTO;
    }

    List<Event> filterEvents(List<Event> events, SearchBean searchBean) {

        //TODO: Something is terribly wrong here :(

        Set<String> groupExcludeTerms = searchBean.getExcludeTerms(SearchBean.SearchField.GROUP);
        Set<String> teacherExcludeTerms = searchBean.getExcludeTerms(SearchBean.SearchField.TEACHER);
        Set<String> roomExcludeTerms = searchBean.getExcludeTerms(SearchBean.SearchField.ROOM);
        Set<String> allExcludeTerms = searchBean.getExcludeTerms(SearchBean.SearchField.ALL);
        return events.stream()
                .filter(event -> {
                    for (String term : groupExcludeTerms) {
                        if (event.getGroups().contains(term)) {
                            return false;
                        }
                    }
                    for (String term : teacherExcludeTerms) {
                        if (event.getTeacher().contains(term)) {
                            return false;
                        }
                    }
                    for (String term : roomExcludeTerms) {
                        if (event.getRooms().contains(term)) {
                            return false;
                        }
                    }
                    for (String term : allExcludeTerms) {
                        if (event.getSummary().contains(term)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    String getSearchTermIDs(Collection<String> searchTerms, Collection<ReferenceData> referenceData) {
        return referenceData.parallelStream()
                .filter(rd -> {
                    for (String term : searchTerms) {
                        if (rd.getName().toLowerCase().contains(term.toLowerCase())) {
                            return true;
                        }
                    }
                    return false;
                })
                .map(r -> String.valueOf(r.getId()))
                .sorted()
                .collect(Collectors.joining(","));
    }

    @Cacheable(CacheConfiguration.EVENTS_CACHE)
    public List<Event> getEvents(Date dateTo, Date dateFrom, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups, List<String> excludes) {
        Map<String, String> params = new HashMap<>();
        params.put(URL_PARAM_DATE_FROM, String.valueOf(dateTo.getTime() / 1000));
        params.put(URL_PARAM_DATE_TO, String.valueOf(dateFrom.getTime() / 1000));
        params.put(URL_PARAM_LANG, lang);
        params.put(PARAM_TEACHERS, StringUtils.collectionToCommaDelimitedString(teachers));
        params.put(PARAM_ROOMS, StringUtils.collectionToCommaDelimitedString(rooms));
        params.put(PARAM_GROUPS, StringUtils.collectionToCommaDelimitedString(groups));

        String responseBody = restTemplate.getForObject(eventURL, String.class, params);
        String json = convertJsonPToJsonString(responseBody);
        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            return parseEventsJson(json);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<Event> parseEventsJson(String json) {
        Map<String, Map<Integer, String>> referenceData = getAllReferenceDataAsMap();
        Map<String, Object> eventListMap = jsonParser.parseMap(json);
        List<Event> eventList = new LinkedList<>();
        List<Map<String, Object>> events = (List<Map<String, Object>>) eventListMap.get(EVENTS);
        for (Map<String, Object> eventObject : events) {
            Event event = new Event();
            event.setId((Integer)eventObject.get(EVENT_ID));
            event.setName((String)eventObject.get(EVENT_NAME));
            event.setComment((String)eventObject.get(EVENT_COMMENT));
            event.setType((String)eventObject.get(EVENT_TYPE));
            event.setTimestamp((Integer)eventObject.get(EVENT_TIME));
            try {
                Integer teacher = Integer.valueOf((String) eventObject.get(EVENT_TEACHER_ID));
                event.setTeacher(getTeacherById(referenceData, teacher));
            } catch (NumberFormatException ignore){
                event.setTeacher("");
            }
            List<Integer> rooms = (List<Integer>) eventObject.get(EVENT_ROOM_IDS);
            event.setRooms(getRoomsByIds(referenceData, rooms));
            List<Integer> groups = (List<Integer>) eventObject.get(EVENT_GROUP_IDS);
            event.setGroups(getGroupsByIds(referenceData, groups));
            eventList.add(event);
        }
        return eventList;
    }

    protected String getGroupsByIds(Map<String, Map<Integer, String>> referenceData, List<Integer> groupIds) {
        Map<Integer, String> groupReferenceData = referenceData.get(PARAM_GROUPS);
        if (groupReferenceData == null || groupIds == null) {
            return EMPTY_STRING;
        }
        List<String> groups = groupIds.stream()
                .map(groupId -> groupReferenceData.getOrDefault(groupId, getDefaultValue(groupId)))
                .collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(groups, DELIMITER);
    }

    protected String getRoomsByIds(Map<String, Map<Integer, String>> referenceData, List<Integer> roomIds) {
        Map<Integer, String> roomReferenceData = referenceData.get(PARAM_ROOMS);
        if (roomReferenceData == null || roomIds == null) {
            return EMPTY_STRING;
        }
        List<String> rooms = roomIds.stream()
                .map(roomId -> roomReferenceData.getOrDefault(roomId, getDefaultValue(roomId)))
                .collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(rooms, DELIMITER);
    }

    protected String getTeacherById(Map<String, Map<Integer, String>> referenceData, Integer teacherId) {
        Map<Integer, String> teachers = referenceData.get(PARAM_TEACHERS);
        if (teachers == null || teacherId == null) {
            return EMPTY_STRING;
        }
        return teachers.getOrDefault(teacherId, getDefaultValue(teacherId));
    }

    protected String getDefaultValue(Integer objectId) {
        return "[id:" + objectId + "]";
    }

    protected Map<String, Map<Integer, String>> getAllReferenceDataAsMap() {
        Map<String, Map<Integer, String>> referenceData = new HashMap<>();
        Map<String, List<ReferenceData>> rd = getReferenceData("en", new String[]{PARAM_GROUPS, PARAM_ROOMS, PARAM_TEACHERS});
        for (String type : rd.keySet()) {
            Map<Integer, String> values = new HashMap<>();
            for (ReferenceData data : rd.get(type)) {
                values.put(data.getId(), data.getName());
            }
            referenceData.put(type, values);
        }
        return referenceData;
    }

    protected String convertJsonPToJsonString(String jsonP) {
        if (jsonP == null || jsonP.isEmpty() || jsonP.length() < 13) {
            return "";
        }
        // Strip all JSONP stuff and other junk
        return jsonP.substring(10, jsonP.length() - 3)
                .replace("\\", "")
                .replace(")foo(", "");
    }

    @Value("${tsi.url.referencedata}")
    public void setReferenceDataURL(String referenceDataURL) {
        this.referenceDataURL = referenceDataURL;
    }

    @Value("${tsi.url.events}")
    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    @Autowired
    public void setSearchQueryProcessor(SearchQueryProcessor searchQueryProcessor) {
        this.searchQueryProcessor = searchQueryProcessor;
    }

    @Autowired
    public void setApplicationTimeService(ApplicationTimeService applicationTimeService) {
        this.applicationTimeService = applicationTimeService;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
