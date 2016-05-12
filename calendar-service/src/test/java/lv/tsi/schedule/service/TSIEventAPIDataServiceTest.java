package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TSIEventAPIDataServiceTest {

    public static final String JSON_P = "foo({\"d\":\"{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}\"})";
    public static final String JSON = "{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}";

    private TSIEventAPIDataService dataService = new TSIEventAPIDataService();
    private RestTemplate restTemplate = mock(RestTemplate.class);

    @Before
    public void setUp() throws Exception {
        dataService.setRestTemplate(restTemplate);
    }

    @Test
    public void convertJsonPToJsonString() throws Exception {
        assertEquals(JSON, dataService.convertJsonPToJsonString(JSON_P));
    }

    @Test
    public void testGetReferenceData() throws Exception {
        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn("");
        Map<String, List<ReferenceData>> emptyData = dataService.getReferenceData("lv", new String[]{"windows"});
        assertTrue("Map should be empty", emptyData.isEmpty());

        when(restTemplate.getForObject(anyString(), any(), anyMapOf(String.class, String.class))).thenReturn(JSON_P);
        Map<String, List<ReferenceData>> data = dataService.getReferenceData("lv", new String[]{"windows"});
        List<ReferenceData> groups = data.get("groups");
        assertNotNull("Map should contain one category named 'groups'", groups);
        assertEquals("Result should contain 3 elements", 3, groups.size());
        assertTrue("Result contains reference data", groups.contains(new ReferenceData(96, "1001BDs")));
    }


}