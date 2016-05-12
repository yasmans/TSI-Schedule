package lv.tsi.schedule.api;

import lv.tsi.schedule.domain.ReferenceData;
import lv.tsi.schedule.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReferenceDataRestController {

    private DataService dataService;

    @RequestMapping(value = "/referenceData", method = RequestMethod.GET)
    public Map<String, List<ReferenceData>> getReferenceData(
            @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "type", required = false) String[] types) {

        //TODO: Validate input

        return dataService.getReferenceData(lang, types);
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
