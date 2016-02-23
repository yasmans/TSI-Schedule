package lv.tsi.schedule;

import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WAPParserServiceTest {

    @Test
    public void testGetHTMLDocument() throws Exception {
        WAPParserService wapParserService = new WAPParserService();
        Element schedulerTable = wapParserService.getHTMLDocument("http://m.tsi.lv/schedule/default.aspx?login=st46983&offset=18");
        assertNotNull(schedulerTable);
    }
}