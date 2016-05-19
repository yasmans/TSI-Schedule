package lv.tsi.calendar.web;

import lv.tsi.calendar.domain.Event;
import lv.tsi.calendar.domain.ReferenceData;
import lv.tsi.calendar.domain.SearchBean;
import lv.tsi.calendar.domain.UIEvent;
import lv.tsi.calendar.service.ApplicationTimeService;
import lv.tsi.calendar.service.DataService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    public static final String PAGE_INDEX = "index";

    private DataService dataService;
    private ApplicationTimeService applicationTimeService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        populateReferenceData(model);
        model.addAttribute("searchBean", new SearchBean());
        return PAGE_INDEX;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.POST)
    public String getCalendarData(@ModelAttribute SearchBean searchBean, Model model) {
        populateReferenceData(model);
        String filterType = searchBean.getFilterType().trim();
        Date currentDate = applicationTimeService.getCurrentDate();
        Date to = DateUtils.addDays(currentDate, 181);
        List<Event> events = new ArrayList<>();
        String language = LocaleContextHolder.getLocale().getLanguage();
        if ("teacher".equals(filterType)) {
            events = dataService.getEvents(currentDate, to, language,Collections.singletonList(searchBean.getTeacherValue()),null,null);
        } else if("group".equals(filterType)) {
            events = dataService.getEvents(currentDate, to, language, null, null, Collections.singletonList(searchBean.getGroupValue()));
        } else if ("room".equals(filterType)) {
            events = dataService.getEvents(currentDate, to, language, null, Collections.singletonList(searchBean.getRoomValue()), null);
        }
        model.addAttribute("events", convertEvents(events));
        model.addAttribute("calendarURL", generateCalendarURL(searchBean));
        return PAGE_INDEX;
    }

    private String generateCalendarURL(SearchBean searchBean) {
        String language = LocaleContextHolder.getLocale().getLanguage();
        //TODO: implement method
        return "http://localhost:8080/home";
    }

    protected List<UIEvent> convertEvents(List<Event> source) {
        return source.stream()
                .map(sourceEvent -> new UIEvent(sourceEvent.getTimestamp(), sourceEvent.getSummary()))
                .collect(Collectors.toList());
    }

    protected void populateReferenceData(Model model) {
        Map<String, List<ReferenceData>> referenceData = dataService.getReferenceData("en", new String[]{"all"});
        for (String type : referenceData.keySet()) {
            model.addAttribute(type, referenceData.get(type));
        }
    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Autowired
    public void setApplicationTimeService(ApplicationTimeService applicationTimeService) {
        this.applicationTimeService = applicationTimeService;
    }
}
