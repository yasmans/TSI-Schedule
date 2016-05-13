package lv.tsi.schedule.service;

import net.fortuna.ical4j.model.Calendar;

import java.util.List;

public interface CalendarService {
    Calendar getCalendar(Long from, Long to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups);
}
