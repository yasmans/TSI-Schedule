package lv.tsi.calendar.api;

import lv.tsi.calendar.dto.EventsDTO;
import lv.tsi.calendar.exceptions.ParameterValidationException;
import lv.tsi.calendar.service.TSIEventAPIDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lv.tsi.calendar.validator.ParameterValidator.validateLanguage;

@RestController
public class EventRestController {

    private TSIEventAPIDataService dataService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public EventsDTO searchEvents(@RequestParam(value = "q") String searchQuery,
                                  @RequestParam(value = "lang", defaultValue = "en") String lang) {
        String validationResults = validateLanguage(lang);
        if (!validationResults.isEmpty()) {
            throw new ParameterValidationException(validationResults);
        }
        return dataService.searchEvents(searchQuery, lang);
    }

    @Autowired
    public void setDataService(TSIEventAPIDataService dataService) {
        this.dataService = dataService;
    }
}
