package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.Event;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
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
            "END:VCALENDAR\n";
    public static final String EXAMPLE_EVENT = "BEGIN:VEVENT\n" +
            "UID:54325\n" +
            "DTSTAMP:20160513T145949Z\n" +
            "DTSTART:20160513T061500Z\n" +
            "DTEND:20160513T173000Z\n" +
            "SUMMARY:L3 Laboratorijas darbs Ivars Holcs\n" +
            "LOCATION:L3\n" +
            "DESCRIPTION:nav atcelts\n" +
            "END:VEVENT";

    @Test
    public void testGetCalendar() throws Exception {
        calendarService.setDataService(dataService);
        List<Event> eventList = new ArrayList<>();
        when(dataService.getEvents(anyLong(), anyLong(), anyString(), anyListOf(Integer.class), anyListOf(Integer.class), anyListOf(Integer.class)))
                .thenReturn(eventList);
        Calendar calendar = calendarService.getCalendar(1L, 12L, "en", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertEquals("Service did not return any events so empty calendar should be returned", EMPTY_CALENDAR, calendar.toString().replace("\r\n", "\n"));

        //TODO: Test valid calendar with two events
    }

    @Test
    public void testCreateEvent() throws Exception {
        /*
            BEGIN:VEVENT
            UID:54325
            DTSTAMP:20160513T145949Z
            DTSTART:20160513T061500Z
            DTEND:20160513T173000Z
            SUMMARY:L3 Laboratorijas darbs Ivars Holcs
            LOCATION:L3
            DESCRIPTION:nav atcelts
            END:VEVENT
         */
        calendarService.setApplicationTimeService(applicationTimeService);
        when(applicationTimeService.getCurrentTimestamp()).thenReturn(1463155522L);
        Event event = new Event(54325, "Laboratorijas darbs", "nav atcelts", 1463142500, "Ivars Holcs", "L3", "4102BNL, 41344LN");
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