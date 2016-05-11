package lv.tsi.schedule.api;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReferenceDataRestControllerTest {

    @Test
    @Ignore
    public void name() throws Exception {
        assertFalse("This test should fail", true);
    }

    @Test
    public void testGreeter() throws Exception {
        ReferenceDataRestController controller = new ReferenceDataRestController();
        assertEquals("Hello Janka", controller.greeting("Janka"));
    }
}