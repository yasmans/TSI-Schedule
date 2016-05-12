package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TSIEventAPIDataService implements DataService {

    public static final String URL_PARAM_LANG = "lang";
    public static final String URL_PARAM_TYPE = "type";

    private String referenceDataURL;
    private RestTemplate restTemplate = new RestTemplate();
    private JacksonJsonParser jsonParser = new JacksonJsonParser();

    public Map<String, List<ReferenceData>> getReferenceData(String lang, String[] types) {
        Map<String, String> params = new HashMap<>();
        params.put(URL_PARAM_LANG, lang);
        params.put(URL_PARAM_TYPE, StringUtils.arrayToCommaDelimitedString(types));

        String responseBody = restTemplate.getForObject(referenceDataURL, String.class, params);
        String json = convertJsonPToJsonString(responseBody);
        if (json.isEmpty()) {
            return new HashMap<>();
        } else {
            return parseReferenceDataJson(json);
        }
    }

    protected Map<String, List<ReferenceData>> parseReferenceDataJson(String json) {
        Map<String, Object> typeToReferenceDataListMap = jsonParser.parseMap(json);
        Map<String, List<ReferenceData>> result = new HashMap<>();
        for (String key : typeToReferenceDataListMap.keySet()) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> referenceDataMapList = (List<Map<String, Object>>)  typeToReferenceDataListMap.get(key);
            List<ReferenceData> referenceDataList = referenceDataMapList.stream()
                    .map(map -> new ReferenceData((Integer) map.get("id"), (String) map.get("name")))
                    .collect(Collectors.toList());
            result.put(key, referenceDataList);
        }
        return result;
    }

    protected String convertJsonPToJsonString(String jsonP) {
        // Strip all JSONP stuff and other junk
        if (jsonP == null || jsonP.isEmpty() || jsonP.length() < 13) {
            return "";
        }
        return jsonP.substring(10, jsonP.length() - 3)
                .replace("\\", "")
                .replace(")foo(", "");
    }

    @Value("${tsi.referencedata.url}")
    public void setReferenceDataURL(String referenceDataURL) {
        this.referenceDataURL = referenceDataURL;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
