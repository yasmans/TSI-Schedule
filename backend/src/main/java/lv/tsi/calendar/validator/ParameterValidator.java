package lv.tsi.calendar.validator;

import java.util.Arrays;

public class ParameterValidator {

    public static String validateLanguage(String language) {
        if (!Arrays.asList("lv", "en", "ru").contains(language)) {
            return "'" + language + "' is not a valid language. possible values: lv, en, ru.\n";
        } else {
            return "";
        }
    }

}
