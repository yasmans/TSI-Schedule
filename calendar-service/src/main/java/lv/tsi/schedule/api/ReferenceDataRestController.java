package lv.tsi.schedule.api;

import lv.tsi.schedule.domain.ReferenceData;
import lv.tsi.schedule.exceptions.ParameterValidationException;
import lv.tsi.schedule.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ReferenceDataRestController {

    private DataService dataService;

    @RequestMapping(value = "/referenceData", method = RequestMethod.GET)
    public Map<String, List<ReferenceData>> getReferenceData(
            @RequestParam(value = "type", defaultValue = "all") String[] types,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {
        String validationErrors = validateRequestParameters(types, lang);
        if (!validationErrors.isEmpty()) {
            throw new ParameterValidationException(validationErrors);
        }
        return dataService.getReferenceData(lang, types);
    }

    protected String validateRequestParameters(String[] types, String lang) {
        StringBuilder sb = new StringBuilder();
        if (!Arrays.asList("lv", "en", "ru").contains(lang)) {
            sb.append("'").append(lang).append("' is not a valid language. possible values: lv, en, ru.\n");
        }
        for (String type : types) {
            if (!Arrays.asList("all", "groups", "teachers", "rooms").contains(type)) {
                sb.append("'").append(type).append("' is not a valid type. possible values: all, groups, teachers, rooms.\n");
            }
        }
        return sb.toString();
    }

    @ExceptionHandler(value = ParameterValidationException.class)
    public ResponseEntity<String> validationExceptionHandler(ParameterValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
