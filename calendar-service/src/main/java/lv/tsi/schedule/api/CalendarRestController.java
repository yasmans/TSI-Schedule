package lv.tsi.schedule.api;

import lv.tsi.schedule.service.CalendarService;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CalendarRestController {

    private CalendarService calendarService;

    @RequestMapping(value = "/events/{from}/{to}/{lang}", method = RequestMethod.GET, produces = "text/calendar")
    public Calendar getCalendar(@PathVariable("from") Long from,
                                @PathVariable("to") Long to,
                                @PathVariable("lang") String lang,
                                @RequestParam(value = "teachers", required = false) List<Integer> teachers,
                                @RequestParam(value = "rooms", required = false) List<Integer> rooms,
                                @RequestParam(value = "groups", required = false) List<Integer> groups) {

        /* TODO: validate params
            from and to must be UNIX timestamps
            lang must be one of lv|ru|en
            teachers rooms and groups can consist only from integers
            there must be specified at least one of teachers rooms and groups
        */
        return calendarService.getCalendar(from, to, lang, teachers, rooms, groups);
    }

    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
}
