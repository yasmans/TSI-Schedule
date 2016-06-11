package lv.tsi.calendar.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UIEvent {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");

    private String title;
    private String start;
    private boolean allday = false;

    public UIEvent(long timestamp, String summary) {
        this.title = summary;
        this.start = sdf.format(new Date(timestamp));
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public boolean isAllday() {
        return allday;
    }

    public void setAllday(boolean allday) {
        this.allday = allday;
    }
}
