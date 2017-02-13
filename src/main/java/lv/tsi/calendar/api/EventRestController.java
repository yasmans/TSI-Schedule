package lv.tsi.calendar.api;

import lv.tsi.calendar.domain.Event;
import lv.tsi.calendar.domain.params.URLDateParam;
import lv.tsi.calendar.exceptions.ParameterValidationException;
import lv.tsi.calendar.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.List;

import static lv.tsi.calendar.validator.ParameterValidator.validateLanguage;
import static lv.tsi.calendar.validator.ParameterValidator.validateSearchParameters;

@RestController
public class EventRestController {

    private DataService dataService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(value = "from") URLDateParam from,
                                 @RequestParam(value = "to") URLDateParam to,
                                 @RequestParam(value = "lang", defaultValue = "en") String lang,
                                 @RequestParam(value = "teachers", required = false) List<Integer> teachers,
                                 @RequestParam(value = "rooms", required = false) List<Integer> rooms,
                                 @RequestParam(value = "excludes", required = false) List<String> excludes,
                                 @RequestParam(value = "groups", required = false) List<Integer> groups) throws IOException, ValidationException {

        String validationResults = validateParameters(lang, teachers, rooms, groups);
        if (!validationResults.isEmpty()) {
            throw new ParameterValidationException(validationResults);
        }
        return dataService.getEvents(from.getDate(), to.getDate(), lang, teachers, rooms, groups, excludes);
    }

    private String validateParameters(String lang, List<Integer> teachers, List<Integer> rooms, List<Integer> groups) {
        return validateLanguage(lang) + validateSearchParameters(teachers, rooms, groups);
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
