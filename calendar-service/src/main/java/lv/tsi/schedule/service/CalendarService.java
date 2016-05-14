package lv.tsi.schedule.service;

import net.fortuna.ical4j.model.Calendar;

import java.util.Date;
import java.util.List;

public interface CalendarService {
    Calendar getCalendar(Date from, Date to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups);
}
