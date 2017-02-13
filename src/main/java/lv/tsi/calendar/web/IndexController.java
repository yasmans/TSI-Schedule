package lv.tsi.calendar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private static final String PAGE_INDEX = "index";

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String getIndexPage() {
        return PAGE_INDEX;
    }

}
