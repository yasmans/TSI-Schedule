package lv.tsi.calendar.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void testGetSummary() throws Exception {
        Event event = new Event();
        assertEquals("Testēšanas variants 1","", event.getSummary());

        event.setTeacher("ABC");
        assertEquals("Testēšanas variants 2","ABC", event.getSummary());

        event.setTeacher(null);
        event.setGroups("123");
        assertEquals("Testēšanas variants 3","123", event.getSummary());

        event.setName("aaa");
        event.setTeacher("ABC");
        event.setGroups("123");
        assertEquals("Testēšanas variants 4","aaa | ABC | 123", event.getSummary());
    }
}