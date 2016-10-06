package lv.tsi.calendar.api;

import lv.tsi.calendar.domain.params.URLDateParam;
import lv.tsi.calendar.exceptions.ParameterValidationException;
import lv.tsi.calendar.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lv.tsi.calendar.validator.ParameterValidator.validateLanguage;
import static lv.tsi.calendar.validator.ParameterValidator.validateSearchParameters;

@RestController
public class CalendarRestController {

    private CalendarService calendarService;
    private CalendarOutputter calendarOutputter = new CalendarOutputter();

    @RequestMapping(value = "/calendar/{from}/{to}/calendar.ics", method = RequestMethod.GET)
    public void getCalendar(@PathVariable("from") URLDateParam from,
                            @PathVariable("to") URLDateParam to,
                            @RequestParam(value = "lang", defaultValue = "en") String lang,
                            @RequestParam(value = "teachers", required = false) List<Integer> teachers,
                            @RequestParam(value = "rooms", required = false) List<Integer> rooms,
                            @RequestParam(value = "groups", required = false) List<Integer> groups,
                            @RequestParam(value = "excludes", required = false) List<String> excludes,
                            HttpServletResponse response) throws IOException, ValidationException {

        String validationResults = validateParameters(lang, teachers, rooms, groups);
        if (excludes == null) {
            excludes = new ArrayList<>();
        }
        if (!validationResults.isEmpty()) {
            throw new ParameterValidationException(validationResults);
        }

        response.addHeader("Content-disposition", "attachment;filename=calendar.ics");
        response.setContentType("txt/calendar");

        Calendar calendar = calendarService.getCalendar(from.getDate(), to.getDate(), lang, teachers, rooms, groups, excludes);
        calendarOutputter.output(calendar, response.getOutputStream());
        response.flushBuffer();
    }

    private String validateParameters(String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups) {
        return validateLanguage(lang) + validateSearchParameters(teachers, rooms, groups);
    }

    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void setCalendarOutputter(CalendarOutputter calendarOutputter) {
        this.calendarOutputter = calendarOutputter;
    }
}
