package lv.tsi.calendar.web;

import lv.tsi.calendar.domain.SearchBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    public static final String PAGE_INDEX = "index";

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        model.addAttribute("searchBean", new SearchBean());
        return PAGE_INDEX;
    }

}
