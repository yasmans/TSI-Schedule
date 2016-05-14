package lv.tsi.schedule.api;

import lv.tsi.schedule.domain.params.URLDateParam;
import lv.tsi.schedule.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class CalendarRestController {

    private CalendarService calendarService;
    private CalendarOutputter calendarOutputter = new CalendarOutputter();

    @RequestMapping(value = "/calendar/{from}/{to}/{lang}", method = RequestMethod.GET)
    public void getCalendar(@PathVariable("from") URLDateParam from,
                            @PathVariable("to") URLDateParam to,
                            @PathVariable("lang") String lang,
                            @RequestParam(value = "teachers", required = false) List<Integer> teachers,
                            @RequestParam(value = "rooms", required = false) List<Integer> rooms,
                            @RequestParam(value = "groups", required = false) List<Integer> groups,
                            HttpServletResponse response) {

        /* TODO: validate params
            from and to must be UNIX timestamps
            lang must be one of lv|ru|en
            teachers rooms and groups can consist only from integers
            there must be specified at least one of teachers rooms and groups
        */
        response.addHeader("Content-disposition", "attachment;filename=calendar.ics");
        response.setContentType("txt/calendar");

        Calendar calendar = calendarService.getCalendar(from.getDate(), to.getDate(), lang, teachers, rooms, groups);
        try {
            calendarOutputter.output(calendar, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException | ValidationException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void setCalendarOutputter(CalendarOutputter calendarOutputter) {
        this.calendarOutputter = calendarOutputter;
    }
}
