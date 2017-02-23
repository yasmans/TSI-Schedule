package lv.tsi.calendar.api;

import lv.tsi.calendar.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CalendarRestController {

    private CalendarService calendarService;
    private CalendarOutputter calendarOutputter = new CalendarOutputter();

    @RequestMapping(value = "/calendar.ics", method = RequestMethod.GET)
    public void getCalendar(@RequestParam(value = "q") String searchQuery,
                            @RequestParam(value = "lang", defaultValue = "en") String lang,
                            HttpServletResponse response) throws IOException, ValidationException {

        response.addHeader("Content-disposition", "attachment;filename=calendar.ics");
        response.setContentType("txt/calendar");

        Calendar calendar = calendarService.getCalendar(searchQuery, lang);
        calendarOutputter.output(calendar, response.getOutputStream());
        response.flushBuffer();
    }


    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void setCalendarOutputter(CalendarOutputter calendarOutputter) {
        this.calendarOutputter = calendarOutputter;
    }
}
