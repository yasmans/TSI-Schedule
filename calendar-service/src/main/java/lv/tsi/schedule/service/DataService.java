package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.Event;
import lv.tsi.schedule.domain.ReferenceData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataService {
    Map<String,List<ReferenceData>> getReferenceData(String lang, String[] types);
    List<Event> getEvents(Date from, Date to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups);
}
