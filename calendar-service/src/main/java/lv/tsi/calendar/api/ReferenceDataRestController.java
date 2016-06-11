package lv.tsi.calendar.api;

import lv.tsi.calendar.domain.ReferenceData;
import lv.tsi.calendar.exceptions.ParameterValidationException;
import lv.tsi.calendar.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static lv.tsi.calendar.validator.ParameterValidator.validateLanguage;
import static lv.tsi.calendar.validator.ParameterValidator.validateReferenceDataType;

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

    private String validateRequestParameters(String[] types, String lang) {
        return validateLanguage(lang) + validateReferenceDataType(types);
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
