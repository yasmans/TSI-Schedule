package lv.tsi.schedule.api;

import lv.tsi.schedule.domain.params.URLDateParam;
import lv.tsi.schedule.exceptions.ParameterValidationException;
import lv.tsi.schedule.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static lv.tsi.schedule.validator.ParameterValidator.validateLanguage;
import static lv.tsi.schedule.validator.ParameterValidator.validateSearchParameters;

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
                            HttpServletResponse response) throws IOException, ValidationException {

        String validationResults = validateParameters(lang, teachers, rooms, groups);
        if (!validationResults.isEmpty()) {
            throw new ParameterValidationException(validationResults);
        }

        response.addHeader("Content-disposition", "attachment;filename=calendar.ics");
        response.setContentType("txt/calendar");

        Calendar calendar = calendarService.getCalendar(from.getDate(), to.getDate(), lang, teachers, rooms, groups);
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
