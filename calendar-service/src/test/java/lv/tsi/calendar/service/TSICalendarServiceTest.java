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
            "BEGIN:VTIMEZONE\n" +
            "TZID:Europe/Riga\n" +
            "TZURL:http://tzurl.org/zoneinfo/Europe/Riga\n" +
            "X-LIC-LOCATION:Europe/Riga\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:20010325T030000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:20011028T040000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+013634\n" +
            "TZNAME:RMT\n" +
            "DTSTART:18800101T000000\n" +
            "RDATE:18800101T000000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+023634\n" +
            "TZNAME:LST\n" +
            "DTSTART:19180415T022326\n" +
            "RDATE:19180415T022326\n" +
            "RDATE:19190401T022326\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+023634\n" +
            "TZOFFSETTO:+013634\n" +
            "TZNAME:RMT\n" +
            "DTSTART:19180916T032326\n" +
            "RDATE:19180916T032326\n" +
            "RDATE:19190522T032326\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19260511T002326\n" +
            "RDATE:19260511T002326\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19400805T000000\n" +
            "RDATE:19400805T000000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:CEST\n" +
            "DTSTART:19410701T010000\n" +
            "RDATE:19410701T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0100\n" +
            "TZNAME:CET\n" +
            "DTSTART:19421102T040000\n" +
            "RDATE:19421102T040000\n" +
            "RDATE:19431004T040000\n" +
            "RDATE:19441002T040000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0100\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:CEST\n" +
            "DTSTART:19430329T030000\n" +
            "RDATE:19430329T030000\n" +
            "RDATE:19440403T030000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0100\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19441013T010000\n" +
            "RDATE:19441013T010000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0400\n" +
            "TZNAME:MSD\n" +
            "DTSTART:19810331T230000\n" +
            "RDATE:19810331T230000\n" +
            "RDATE:19820331T230000\n" +
            "RDATE:19830331T230000\n" +
            "RDATE:19840331T230000\n" +
            "RDATE:19850331T010000\n" +
            "RDATE:19860330T010000\n" +
            "RDATE:19870329T010000\n" +
            "RDATE:19880327T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0400\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19810930T230000\n" +
            "RDATE:19810930T230000\n" +
            "RDATE:19820930T230000\n" +
            "RDATE:19830930T230000\n" +
            "RDATE:19840930T020000\n" +
            "RDATE:19850929T020000\n" +
            "RDATE:19860928T020000\n" +
            "RDATE:19870927T020000\n" +
            "RDATE:19880925T020000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:19890326T010000\n" +
            "RDATE:19890326T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19890924T030000\n" +
            "RDATE:19890924T030000\n" +
            "RDATE:19900930T030000\n" +
            "RDATE:19910929T030000\n" +
            "RDATE:19920927T030000\n" +
            "RDATE:19930926T030000\n" +
            "RDATE:19940925T030000\n" +
            "RDATE:19950924T030000\n" +
            "RDATE:19960929T030000\n" +
            "RDATE:19971026T040000\n" +
            "RDATE:19981025T040000\n" +
            "RDATE:19991031T040000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:19900325T020000\n" +
            "RDATE:19900325T020000\n" +
            "RDATE:19910331T020000\n" +
            "RDATE:19920329T020000\n" +
            "RDATE:19930328T020000\n" +
            "RDATE:19940327T020000\n" +
            "RDATE:19950326T020000\n" +
            "RDATE:19960331T020000\n" +
            "RDATE:19970330T030000\n" +
            "RDATE:19980329T030000\n" +
            "RDATE:19990328T030000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19970121T000000\n" +
            "RDATE:19970121T000000\n" +
            "RDATE:20000229T000000\n" +
            "RDATE:20010102T000000\n" +
            "END:STANDARD\n" +
            "END:VTIMEZONE\n" +
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
            "TZID:Europe/Riga\n" +
            "END:VEVENT\n";
    public static final String CALENDAR_WITH_EVENTS = "BEGIN:VCALENDAR\n" +
            "PRODID:-//TSI//Calendar Service//\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "X-PUBLISHED-TTL:PT24H\n" +
            "BEGIN:VTIMEZONE\n" +
            "TZID:Europe/Riga\n" +
            "TZURL:http://tzurl.org/zoneinfo/Europe/Riga\n" +
            "X-LIC-LOCATION:Europe/Riga\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:20010325T030000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:20011028T040000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+013634\n" +
            "TZNAME:RMT\n" +
            "DTSTART:18800101T000000\n" +
            "RDATE:18800101T000000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+023634\n" +
            "TZNAME:LST\n" +
            "DTSTART:19180415T022326\n" +
            "RDATE:19180415T022326\n" +
            "RDATE:19190401T022326\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+023634\n" +
            "TZOFFSETTO:+013634\n" +
            "TZNAME:RMT\n" +
            "DTSTART:19180916T032326\n" +
            "RDATE:19180916T032326\n" +
            "RDATE:19190522T032326\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+013634\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19260511T002326\n" +
            "RDATE:19260511T002326\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19400805T000000\n" +
            "RDATE:19400805T000000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:CEST\n" +
            "DTSTART:19410701T010000\n" +
            "RDATE:19410701T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0100\n" +
            "TZNAME:CET\n" +
            "DTSTART:19421102T040000\n" +
            "RDATE:19421102T040000\n" +
            "RDATE:19431004T040000\n" +
            "RDATE:19441002T040000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0100\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:CEST\n" +
            "DTSTART:19430329T030000\n" +
            "RDATE:19430329T030000\n" +
            "RDATE:19440403T030000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0100\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19441013T010000\n" +
            "RDATE:19441013T010000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0400\n" +
            "TZNAME:MSD\n" +
            "DTSTART:19810331T230000\n" +
            "RDATE:19810331T230000\n" +
            "RDATE:19820331T230000\n" +
            "RDATE:19830331T230000\n" +
            "RDATE:19840331T230000\n" +
            "RDATE:19850331T010000\n" +
            "RDATE:19860330T010000\n" +
            "RDATE:19870329T010000\n" +
            "RDATE:19880327T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0400\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:MSK\n" +
            "DTSTART:19810930T230000\n" +
            "RDATE:19810930T230000\n" +
            "RDATE:19820930T230000\n" +
            "RDATE:19830930T230000\n" +
            "RDATE:19840930T020000\n" +
            "RDATE:19850929T020000\n" +
            "RDATE:19860928T020000\n" +
            "RDATE:19870927T020000\n" +
            "RDATE:19880925T020000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:19890326T010000\n" +
            "RDATE:19890326T010000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0300\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19890924T030000\n" +
            "RDATE:19890924T030000\n" +
            "RDATE:19900930T030000\n" +
            "RDATE:19910929T030000\n" +
            "RDATE:19920927T030000\n" +
            "RDATE:19930926T030000\n" +
            "RDATE:19940925T030000\n" +
            "RDATE:19950924T030000\n" +
            "RDATE:19960929T030000\n" +
            "RDATE:19971026T040000\n" +
            "RDATE:19981025T040000\n" +
            "RDATE:19991031T040000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0300\n" +
            "TZNAME:EEST\n" +
            "DTSTART:19900325T020000\n" +
            "RDATE:19900325T020000\n" +
            "RDATE:19910331T020000\n" +
            "RDATE:19920329T020000\n" +
            "RDATE:19930328T020000\n" +
            "RDATE:19940327T020000\n" +
            "RDATE:19950326T020000\n" +
            "RDATE:19960331T020000\n" +
            "RDATE:19970330T030000\n" +
            "RDATE:19980329T030000\n" +
            "RDATE:19990328T030000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+0200\n" +
            "TZOFFSETTO:+0200\n" +
            "TZNAME:EET\n" +
            "DTSTART:19970121T000000\n" +
            "RDATE:19970121T000000\n" +
            "RDATE:20000229T000000\n" +
            "RDATE:20010102T000000\n" +
            "END:STANDARD\n" +
            "END:VTIMEZONE\n" +
            "BEGIN:VEVENT\n" +
            "DTSTAMP:20160514T120000Z\n" +
            "UID:54325\n" +
            "DTSTART:20160514T150000\n" +
            "DTEND:20160514T163000\n" +
            "SUMMARY:Laboratorijas darbs | Ivars Holcs | 4102BNL 41344LN\n" +
            "LOCATION:L3\n" +
            "CATEGORIES:\n" +
            "DESCRIPTION:nav atcelts\n" +
            "TZID:Europe/Riga\n" +
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
            "TZID:Europe/Riga\n" +
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