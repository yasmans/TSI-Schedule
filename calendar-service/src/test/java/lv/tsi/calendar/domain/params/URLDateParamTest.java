package lv.tsi.calendar.domain.params;

import lv.tsi.calendar.exceptions.ParameterValidationException;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.validator.cfg.defs.AssertTrueDef;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class URLDateParamTest {

    @Test
    public void testGetDate() throws Exception {
        try {
            new URLDateParam("jfk3434jsdf");
            fail("ParameterValidationException expected here");
        } catch (ParameterValidationException e) {
            assertEquals("Cannot parse 'jfk3434jsdf' expected date format: 'yyyy-MM-dd", e.getMessage());
        }

        URLDateParam urlDateParam = new URLDateParam("2016-05-10");
        assertNotNull(urlDateParam);
    }
}