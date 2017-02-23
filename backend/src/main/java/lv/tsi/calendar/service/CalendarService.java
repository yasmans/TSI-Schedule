package lv.tsi.calendar.service;

import net.fortuna.ical4j.model.Calendar;

public interface CalendarService {
    Calendar getCalendar(String searchQuery, String lang);
}
