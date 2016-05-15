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

    @Test
    public void testValidateReferenceDataType() throws Exception {
        assertEquals("", ParameterValidator.validateReferenceDataType(null));
        assertEquals("", ParameterValidator.validateReferenceDataType(new String[]{}));
        assertEquals("'windows' is not a valid type. possible values: all, groups, teachers, rooms.\n",
                ParameterValidator.validateReferenceDataType(new String[]{"windows"}));
    }

    @Test
    public void testValidateSearchParameters() throws Exception {
        assertEquals("At least one of parameters 'teachers', 'rooms' or 'groups' must contain valid value\n",
                ParameterValidator.validateSearchParameters(null, null, null));
        assertEquals("At least one of parameters 'teachers', 'rooms' or 'groups' must contain valid value\n",
                ParameterValidator.validateSearchParameters(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        assertEquals("",
                ParameterValidator.validateSearchParameters(Arrays.asList(32, 543), new ArrayList<>(), new ArrayList<>()));
        assertEquals("",
                ParameterValidator.validateSearchParameters(new ArrayList<>(), Arrays.asList(32, 543), new ArrayList<>()));
        assertEquals("",
                ParameterValidator.validateSearchParameters(new ArrayList<>(), new ArrayList<>(), Arrays.asList(32, 543)));

    }
}