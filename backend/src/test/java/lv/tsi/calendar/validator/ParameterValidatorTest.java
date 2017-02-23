package lv.tsi.calendar.validator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ParameterValidatorTest {

    @Test
    public void testValidateLanguage() throws Exception {
        assertEquals("", ParameterValidator.validateLanguage("lv"));
        assertEquals("'nl' is not a valid language. possible values: lv, en, ru.\n",
                ParameterValidator.validateLanguage("nl"));
        assertEquals("'null' is not a valid language. possible values: lv, en, ru.\n",
                ParameterValidator.validateLanguage(null));
    }

}