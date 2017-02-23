package lv.tsi.calendar.dto;

import lv.tsi.calendar.domain.Event;
import lv.tsi.calendar.service.search.SearchBean;

import java.util.List;

public class EventsDTO {

    private SearchBean searchBean;
    private List<Event> events;

    public void setSearchBean(SearchBean searchBean) {
        this.searchBean = searchBean;
    }

    public SearchBean getSearchBean() {
        return searchBean;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
