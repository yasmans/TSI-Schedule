package lv.tsi.schedule.api;

import edu.emory.mathcs.backport.java.util.Arrays;
import lv.tsi.schedule.Application;
import lv.tsi.schedule.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CalendarRestControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CalendarRestController calendarRestController;

    private CalendarService calendarService = mock(CalendarService.class);
    private CalendarOutputter calendarOutputter = new CalendarOutputter(false);

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        calendarRestController.setCalendarService(calendarService);
        calendarRestController.setCalendarOutputter(calendarOutputter);
    }

    @Test
    public void testGetCalendar() throws Exception {
        when(calendarService.getCalendar(any(), any(), anyString(), anyListOf(Integer.class), anyListOf(Integer.class),anyListOf(Integer.class)))
                .thenReturn(new Calendar());

        // Get calendar for a week from 09-05-2016 to 15-05-2016
        mockMvc.perform(get("/calendar/2016-05-09/2016-05-15/lv?teachers=11292,8315,18211"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("txt/calendar")));
    }

    //TODO: Test validation
}