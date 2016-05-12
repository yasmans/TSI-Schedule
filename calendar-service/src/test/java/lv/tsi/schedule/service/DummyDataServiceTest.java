package lv.tsi.schedule.service;

import lv.tsi.schedule.domain.ReferenceData;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DummyDataServiceTest {

    private DummyDataService dataService = new DummyDataService();

    @Test
    public void convertJsonPToJsonString() throws Exception {
        String jsonP = "({\"d\":\"{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}\"})";
        String expected = "{\"groups\":[{\"id\":769,\"name\":\"1001BD\"},{\"id\":96,\"name\":\"1001BDs\"},{\"id\":10,\"name\":\"SK\"}]}";
        assertEquals(expected, dataService.convertJsonPToJsonString(jsonP));
    }

    @Test
    public void name() throws Exception {
        Map<String, List<ReferenceData>> data = dataService.getReferenceData("lv", new String[]{"classes"});
        assertNull(data);

    }
}