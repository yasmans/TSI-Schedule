package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.Event;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class TSICalendarService implements CalendarService {

    public static final String TSI_CALENDAR_SERVICE = "-//TSI//Calendar Service//";
    private DataService dataService;

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
        //TODO: This should be reconsidered and formatted properly
        Dur lessonDuration = new Dur(0, 0, 45, 0);
        Date startDate = new Date(event.getTimestamp());
        VEvent vEvent = new VEvent(startDate, lessonDuration, event.getSummary());
        vEvent.getProperties().add(new Location(event.getRooms()));
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
}
