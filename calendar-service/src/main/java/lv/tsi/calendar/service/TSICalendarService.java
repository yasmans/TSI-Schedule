package lv.tsi.calendar.service;

import lv.tsi.calendar.config.CacheConfiguration;
import lv.tsi.calendar.domain.Event;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Date;

@Component
public class TSICalendarService implements CalendarService {

    public static final String TSI_CALENDAR_SERVICE = "-//TSI//Calendar Service//";
    public static final long MILLISECONDS_FROM_90_MINUTES = 90 * 60 * 1000;
    private DataService dataService;
    private ApplicationTimeService applicationTimeService;

    @Cacheable(CacheConfiguration.CALENDAR_CACHE)
    public Calendar getCalendar(Date from, Date to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups) {
        List<Event> events = dataService.getEvents(from, to, lang, teachers, rooms, groups);
        Calendar calendar = createCalendar();
        events.stream()
                .map(this::createEvent)
                .forEach(vEvent -> calendar.getComponents().add(vEvent));
        return calendar;
    }

    protected VEvent createEvent(Event event) {
        DateTime currentDate = new DateTime(applicationTimeService.getCurrentTimestamp());
        DateTime startDate = new DateTime(event.getTimestamp());
        DateTime endDate = new DateTime(event.getTimestamp() + MILLISECONDS_FROM_90_MINUTES);
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Riga");
        VTimeZone tz = timezone.getVTimeZone();
        VEvent vEvent = new VEvent();
        vEvent.getDateStamp().setDateTime(currentDate);
        PropertyList properties = vEvent.getProperties();
        properties.add(new Uid(String.valueOf(event.getId())));
        properties.add(new DtStart(startDate));
        properties.add(new DtEnd(endDate));
        properties.add(new Summary(event.getSummary()));
        properties.add(new Location(event.getRooms()));
        properties.add(new Categories(event.getType()));
        properties.add(new Description(event.getComment()));
        properties.add(tz.getTimeZoneId());
        return vEvent;
    }

    protected Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(TSI_CALENDAR_SERVICE));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Autowired
    public void setApplicationTimeService(ApplicationTimeService applicationTimeService) {
        this.applicationTimeService = applicationTimeService;
    }
}
