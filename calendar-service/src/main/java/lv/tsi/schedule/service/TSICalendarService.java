package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.Event;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class TSICalendarService implements CalendarService {

    public static final String TSI_CALENDAR_SERVICE = "-//TSI//Calendar Service//";
    public static final long MILLISECONDS_FROM_90_MINUTES = 90 * 60 * 1000;
    private DataService dataService;
    private ApplicationTimeService applicationTimeService;

    public Calendar getCalendar(Long from, Long to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups) {
        List<Event> events = dataService.getEvents(from, to, lang, teachers, rooms, groups);
        Calendar calendar = createCalendar();
        if (events.isEmpty()) {
            return calendar;
        }
        events.parallelStream()
                .map(this::createEvent)
                .forEach(vEvent -> calendar.getComponents().add(vEvent));

        return calendar;
    }

    protected VEvent createEvent(Event event) {
        Date startDate = new Date(event.getTimestamp());
        Date endDate = new Date(event.getTimestamp() + MILLISECONDS_FROM_90_MINUTES);
        DateTime currentDate = new DateTime(applicationTimeService.getCurrentTimestamp());
        VEvent vEvent = new VEvent();
        PropertyList properties = vEvent.getProperties();
        properties.add(new Uid(String.valueOf(event.getId())));
        properties.add(new DtStamp(currentDate));
        properties.add(new DtStart(startDate));
        properties.add(new DtEnd(endDate));
        properties.add(new Summary(event.getSummary()));
        properties.add(new Location(event.getRooms()));
        properties.add(new Description(event.getComment()));
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
