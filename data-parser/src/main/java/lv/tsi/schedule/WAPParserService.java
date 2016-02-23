package lv.tsi.schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WAPParserService {

    private static final String URL = "http://m.tsi.lv/schedule/default.aspx?";

    public Element getHTMLDocument(String url) throws IOException {
        return Jsoup.connect(url).get().getElementById("scheduleTable");
    }



}
