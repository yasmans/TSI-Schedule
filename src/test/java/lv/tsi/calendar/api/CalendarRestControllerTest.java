package lv.tsi.calendar.api;

import lv.tsi.calendar.service.CalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        when(calendarService.getCalendar(any(), any(), anyString(), anyListOf(Integer.class), anyListOf(Integer.class),anyListOf(Integer.class), anyListOf(String.class)))
                .thenReturn(new Calendar());

        // Get calendar for a week from 09-05-2016 to 15-05-2016
        mockMvc.perform(get("/calendar/2016-05-09/2016-05-15/calendar.ics?teachers=11292,8315,18211"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("txt/calendar")));
    }

    @Test
    public void testValidation() throws Exception {
        when(calendarService.getCalendar(any(), any(), anyString(), anyListOf(Integer.class), anyListOf(Integer.class),anyListOf(Integer.class), anyListOf(String.class)))
                .thenReturn(new Calendar());

        mockMvc.perform(get("/calendar/2016-05-09/2016-05-15/calendar.ics"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("At least one of parameters 'teachers', 'rooms' or 'groups' must contain valid value\n"));

        mockMvc.perform(get("/calendar/2016-05-09/2016-05-15/calendar.ics?lang=zz"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("'zz' is not a valid language. possible values: lv, en, ru.\n" +
                        "At least one of parameters 'teachers', 'rooms' or 'groups' must contain valid value\n"));
    }
}