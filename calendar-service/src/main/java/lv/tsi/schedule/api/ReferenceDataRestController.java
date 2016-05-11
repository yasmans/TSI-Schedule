package lv.tsi.schedule.api;

import lv.tsi.schedule.domain.Teacher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReferenceDataRestController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "Janka") String name) {
        return "Hello " + name;
    }

    @RequestMapping("/teacher")
    public Teacher getReferenceData() {
        return new Teacher();
    }
}
