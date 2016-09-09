package lv.tsi.calendar.service;

import lv.tsi.calendar.domain.Event;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TSICalendarServiceTest {

    private TSICalendarService calendarService = new TSICalendarService();
    private DataService dataService = mock(DataService.class);
    private ApplicationTimeService applicationTimeService = mock(ApplicationTimeService.class);

    public static final String EMPTY_CALENDAR = "BEGIN:VCALENDAR\n" +
            "PRODID:-//TSI//Calendar Service//\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "X-PUBLISHED-TTL:PT24H\n" +
            "X-WR-TIMEZONE:Europe/Riga\n" +
            "END:VCALENDAR\n";
    public static final String EXAMPLE_EVENT = "BEGIN:VEVENT\n" +
            "DTSTAMP:20160514T120000Z\n" +
            "UID:54325\n" +
            "DTSTART:20160514T150000\n" +
            "DTEND:20160514T163000\n" +
            "SUMMARY:Laboratorijas darbs | Ivars Holcs | 4102BNL 41344LN\n" +
            "LOCATION:L3\n" +
            "CATEGORIES:normal\n" +
            "DESCRIPTION:nav atcelts\n" +
            "END:VEVENT\n";
    public static final String CALENDAR_WITH_EVENTS = "BEGIN:VCALENDAR\n" +
            "PRODID:-//TSI//Calendar Service//\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "X-PUBLISHED-TTL:PT24H\n" +
            "X-WR-TIMEZONE:Europe/Riga\n" +
            "BEGIN:VEVENT\n" +
            "DTSTAMP:20160514T120000Z\n" +
            "UID:54325\n" +
            "DTSTART:20160514T150000\n" +
            "DTEND:20160514T163000\n" +
            "SUMMARY:Laboratorijas darbs | Ivars Holcs | 4102BNL 41344LN\n" +
            "LOCATION:L3\n" +
            "CATEGORIES:\n" +
            "DESCRIPTION:nav atcelts\n" +
            "END:VEVENT\n" +
            "BEGIN:VEVENT\n" +
            "DTSTAMP:20160514T120000Z\n" +
            "UID:4432\n" +
            "DTSTART:20160515T113000\n" +
            "DTEND:20160515T130000\n" +
            "SUMMARY:Skaitliskās metodes | John Snow | 4102BNL\n" +
            "LOCATION:304\n" +
            "CATEGORIES:\n" +
            "DESCRIPTION:\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR\n";

    @Test
    public void testGetCalendar() throws Exception {
        calendarService.setApplicationTimeService(applicationTimeService);
        calendarService.setDataService(dataService);
        when(applicationTimeService.getCurrentTimestamp()).thenReturn(1463227200000L);

        List<Event> eventList = new ArrayList<>();
        when(dataService.getEvents(any(), any(), anyString(), anyListOf(Integer.class), anyListOf(Integer.class), anyListOf(Integer.class)))
                .thenReturn(eventList);
        Calendar emptyCalendar = calendarService.getCalendar(new Date(), new Date(), "en", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertEquals("Service did not return any events so empty calendar should be returned", EMPTY_CALENDAR, emptyCalendar.toString().replace("\r\n", "\n"));

        Event firstEvent = new Event(54325, "Laboratorijas darbs", "nav atcelts", 1463227200000L, "Ivars Holcs", "L3", "4102BNL 41344LN");
        Event secondEvent = new Event(4432, "Skaitliskās metodes", null, 1463301000000L, "John Snow", "304", "4102BNL");
        eventList.add(firstEvent);
        eventList.add(secondEvent);
        Calendar calendar = calendarService.getCalendar(new Date(), new Date(), "en", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertNotNull(calendar);
        assertEquals(CALENDAR_WITH_EVENTS, calendar.toString().replace("\r\n", "\n"));

    }

    @Test
    public void testCreateEvent() throws Exception {
        calendarService.setApplicationTimeService(applicationTimeService);
        when(applicationTimeService.getCurrentTimestamp()).thenReturn(1463227200000L);
        Event event = new Event(54325, "Laboratorijas darbs", "nav atcelts", 1463227200000L, "Ivars Holcs", "L3", "4102BNL 41344LN");
        event.setType("normal");
        VEvent vEvent = calendarService.createEvent(event);
        assertNotNull(vEvent);
        assertEquals(EXAMPLE_EVENT, vEvent.toString().replace("\r\n", "\n"));
    }

    @Test
    public void testCreateCalendar() throws Exception {
        Calendar calendar = calendarService.createCalendar();
        assertEquals("Calendar must contain ID", "-//TSI//Calendar Service//", calendar.getProductId().getValue());
        assertEquals("Empty calendar with basic properties should be generated", EMPTY_CALENDAR,
                calendar.toString().replace("\r\n", "\n"));
    }
}