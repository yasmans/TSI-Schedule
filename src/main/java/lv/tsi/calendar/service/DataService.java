package lv.tsi.calendar.service;

import lv.tsi.calendar.domain.Event;
import lv.tsi.calendar.domain.ReferenceData;
import lv.tsi.calendar.dto.EventsDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataService {
    Map<String,List<ReferenceData>> getReferenceData(String lang, String[] types);
    List<Event> getEvents(Date from, Date to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups, List<String> excludes);
    EventsDTO searchEvents(String searchQuery, String lang);
}
