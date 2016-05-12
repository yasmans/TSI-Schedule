package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DummyDataService implements DataService {

    private String referenceDataURL = "http://services.tsi.lv/schedule/api/service.asmx/GetItems2?type=\"groups\"";

    @Override
    public Map<String, List<ReferenceData>> getReferenceData(String lang, String[] types) {
        RestTemplate restTemplate = new RestTemplate();

//        restTemplate.getForEntity(referenceDataURL, String.class);
//        https://spring.io/blog/2009/03/27/rest-in-spring-3-resttemplate
//        if (HttpStatus.OK.equals(response.getStatusCode())) {
//            String json = convertJsonPToJsonString(response.toString());
//
//        }


        return null;
    }

    public String convertJsonPToJsonString(String jsonP) {
        return jsonP.substring(7, jsonP.length() - 3);
    }
}
