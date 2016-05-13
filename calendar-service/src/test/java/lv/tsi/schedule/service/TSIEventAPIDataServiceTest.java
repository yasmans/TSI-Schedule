package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.Event;
import lv.tsi.schedule.domain.ReferenceData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TSIEventAPIDataServiceTest {

    public static final String JSON_P = "foo({\"d\":\"{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}\"})";
    public static final String JSON = "{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}";
    public static final String EVENTS_JSONP = "foo({\"d\":\"{\"events\": [{ \"id\": 513377,\n \"name\": \"Software Engineering\",\n \"comment\": \"\",\n \"type\": \"normal\",\n \"time\": 1451931300,\n \"teacher\": \"11292\",\n \"room\": [\n 43\n ],\n \"group\": [\n 901,\n 433\n ]\n },\n {\n \"id\": 513107,\n \"name\": \"Software Engineering\",\n \"comment\": \"\",\n \"type\": \"normal\",\n \"time\": 1452017700,\n \"teacher\": \"11292\",\n \"room\": [\n 43\n ],\n \"group\": []\n }\n ]\n }\"})";
    public static final String EVENTS_JSON = "{\"events\": [{ \"id\": 513377,\n \"name\": \"Software Engineering\",\n \"comment\": \"\",\n \"type\": \"normal\",\n \"time\": 1451931300,\n \"teacher\": \"11292\",\n \"room\": [\n 43\n ],\n \"group\": [\n 901,\n 433\n ]\n },\n {\n \"id\": 513107,\n \"name\": \"Software Engineering\",\n \"comment\": \"\",\n \"type\": \"normal\",\n \"time\": 1452017700,\n \"teacher\": \"11292\",\n \"room\": [\n 43\n ],\n \"group\": []\n }\n ]\n }";


    private TSIEventAPIDataService dataService = new TSIEventAPIDataService();
    private RestTemplate restTemplate = mock(RestTemplate.class);

    @Before
    public void setUp() throws Exception {
        dataService.setRestTemplate(restTemplate);
    }

    @Test
    public void convertJsonPToJsonString() throws Exception {
        assertEquals(JSON, dataService.convertJsonPToJsonString(JSON_P));
    }

    @Test
    public void testGetReferenceData() throws Exception {
        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn("");
        Map<String, List<ReferenceData>> emptyData = dataService.getReferenceData("lv", new String[]{"windows"});
        assertTrue("Map should be empty", emptyData.isEmpty());

        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn(JSON_P);
        Map<String, List<ReferenceData>> data = dataService.getReferenceData("lv", new String[]{"windows"});
        List<ReferenceData> groups = data.get("groups");
        assertNotNull("Map should contain one category named 'groups'", groups);
        assertEquals("Result should contain 3 elements", 3, groups.size());
        assertTrue("Result contains reference data", groups.contains(new ReferenceData(96, "1001BDs")));
    }

    @Test
    public void testConvertEventsJsonToEvents() throws Exception {
        List<Event> events = dataService.parseEventsJson(EVENTS_JSON);
        assertNotNull(events);
        assertEquals("Result must contain two elements", 2, events.size());
    }

    @Test
    public void testGetEvents() throws Exception {
        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn("");
        List<Event> emptyData = dataService.getEvents(53215L, 123L, "eng", new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        assertTrue("Map should be empty", emptyData.isEmpty());

        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn(EVENTS_JSONP);
        List<Event> events = dataService.getEvents(53215L, 123L, "eng", new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        assertEquals("Result must contain two elements", 2, events.size());
    }

    @Test
    public void testGetGroupsByIds() throws Exception {
        List<Integer> groupIds = Arrays.asList(34, 44, 443);
        String emptyGroups = dataService.getGroupsByIds(new HashMap<>(), groupIds);
        assertEquals("Groups should be empty because there is no reference data", "", emptyGroups);

        Map<String, Map<Integer, String>> referenceData = new HashMap<>();
        HashMap<Integer, String> refDataMap = new HashMap<>();
        referenceData.put(TSIEventAPIDataService.PARAM_GROUPS, refDataMap);
        refDataMap.put(1, "44jjj");
        refDataMap.put(34, "4102BNL");
        refDataMap.put(44, "22JNSDG");
        String groupsByIds = dataService.getGroupsByIds(referenceData, groupIds);

        assertEquals("Should return valid result string with two found groups and one not found",
                "4102BNL, 22JNSDG, [id:443]", groupsByIds);
    }

    @Test
    public void testGetRoomsByIDs() throws Exception {
        List<Integer> roomIDs = Arrays.asList(1, 2, 345);
        String emptyRooms = dataService.getRoomsByIds(new HashMap<>(), roomIDs);
        assertEquals("Rooms should be empty because there is no reference data", "", emptyRooms);

        Map<String, Map<Integer, String>> referenceData = new HashMap<>();
        HashMap<Integer, String> refDataMap = new HashMap<>();
        referenceData.put(TSIEventAPIDataService.PARAM_ROOMS, refDataMap);
        refDataMap.put(1, "L3");
        refDataMap.put(2, "304");
        refDataMap.put(44, "709");
        String roomsByIDs = dataService.getRoomsByIds(referenceData, roomIDs);

        assertEquals("Should return valid result string with two found rooms and one not found",
                "L3, 304, [id:345]", roomsByIDs);
    }

    @Test
    public void testGetTeacherByID() throws Exception {
        String noTeacher = dataService.getTeacherById(new HashMap<>(), 33);
        assertEquals("Teacher string should be empty", "", noTeacher);

        Map<String, Map<Integer, String>> referenceData = new HashMap<>();
        HashMap<Integer, String> refDataMap = new HashMap<>();
        referenceData.put(TSIEventAPIDataService.PARAM_TEACHERS, refDataMap);
        refDataMap.put(1, "Laima Krūma");
        refDataMap.put(2, "Iga Goga");

        assertEquals("Teacher should be found", "Laima Krūma", dataService.getTeacherById(referenceData, 1));
        assertEquals("No teacher with id exists", "[id:3]", dataService.getTeacherById(referenceData, 3));
    }
}