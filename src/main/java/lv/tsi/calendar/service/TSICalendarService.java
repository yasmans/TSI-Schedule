package lv.tsi.calendar.service;

import lv.tsi.calendar.domain.Event;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TSICalendarService implements CalendarService {

    public static final String TSI_CALENDAR_SERVICE = "-//TSI//Calendar Service//";
    public static final long MILLISECONDS_FROM_90_MINUTES = 90 * 60 * 1000;

    private final TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
    private final TimeZone timezone = registry.getTimeZone("Europe/Riga");
    private final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    private DataService dataService;
    private ApplicationTimeService applicationTimeService;

    public Calendar getCalendar(Date from, Date to, String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups, List<String> excludes) {
        List<Event> events = dataService.getEvents(from, to, lang, teachers, rooms, groups, excludes);
        Calendar calendar = createCalendar();
        events.stream()
                .filter(e -> !excludes.contains(e.getName()))
                .map(this::createEvent)
                .forEach(vEvent -> calendar.getComponents().add(vEvent));
        return calendar;
    }

    protected VEvent createEvent(Event event) {
        VEvent vEvent = new VEvent();
        vEvent.getDateStamp().setDateTime(getDateTime(applicationTimeService.getCurrentTimestamp()));
        PropertyList properties = vEvent.getProperties();
        properties.add(new Uid(String.valueOf(event.getId())));
        properties.add(new DtStart(getDateTime(event.getTimestamp())));
        properties.add(new DtEnd(getDateTime(event.getTimestamp() + MILLISECONDS_FROM_90_MINUTES)));
        properties.add(new Summary(event.getSummary()));
        properties.add(new Location(event.getRooms()));
        properties.add(new Categories(event.getType()));
        properties.add(new Description(event.getComment()));
        return vEvent;
    }

    private DateTime getDateTime(long timestamp) {
        String formattedDate = dateFormat.format(new Date(timestamp));
        try {
            return new DateTime(formattedDate, timezone);
        } catch (ParseException ignore) {
            return null;
        }
    }

    protected Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(TSI_CALENDAR_SERVICE));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(new XProperty("X-PUBLISHED-TTL", "PT24H"));
        calendar.getProperties().add(new XProperty("X-WR-TIMEZONE", "Europe/Riga"));
        calendar.getProperties().add(new XProperty("X-WR-CALNAME", "TSI Calendar"));
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
