package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TSIJsonPDataService implements DataService {

    private String referenceDataURL;

    @Override
    public Map<String, List<ReferenceData>> getReferenceData(String lang, String[] types) {
        Map<String, String> params = new HashMap<>();
        params.put("lang", lang);
        params.put("type", StringUtils.arrayToCommaDelimitedString(types));

        RestTemplate restTemplate = new RestTemplate();

        String object = restTemplate.getForObject(referenceDataURL, String.class, params);
        String json = convertJsonPToJsonString(object);
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> stringObjectMap = jsonParser.parseMap(json);
        Map<String, List<ReferenceData>> result = new HashMap<>();
        for (String key : stringObjectMap.keySet()) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) stringObjectMap.get(key);
            List<ReferenceData> referenceDatas = list.stream()
                    .map(map -> new ReferenceData((Integer) map.get("id"), (String) map.get("name")))
                    .collect(Collectors.toList());
            result.put(key, referenceDatas);
        }
        return result;
    }

    public String convertJsonPToJsonString(String jsonP) {
        // Strip all JSONP stuff and other junk
        return jsonP.substring(10, jsonP.length() - 3)
                .replace("\\", "")
                .replace(")foo(", "");
    }

    @Value("${tsi.referencedata.url}")
    public void setReferenceDataURL(String referenceDataURL) {
        this.referenceDataURL = referenceDataURL;
    }
}
