package lv.tsi.schedule.validator;

import java.util.Arrays;
import java.util.List;

public class ParameterValidator {

    public static String validateLanguage(String language) {
        if (!Arrays.asList("lv", "en", "ru").contains(language)) {
            return "'" + language + "' is not a valid language. possible values: lv, en, ru.\n";
        } else return "";
    }

    public static String validateReferenceDataType(String[] types) {
        StringBuilder sb = new StringBuilder();
        for (String type : types) {
            if (!Arrays.asList("all", "groups", "teachers", "rooms").contains(type)) {
                sb.append("'").append(type).append("' is not a valid type. possible values: all, groups, teachers, rooms.\n");
            }
        }
        return sb.toString();
    }

    public static String validateSearchParameters(List<Integer> teachers, List<Integer> rooms, List<Integer> groups) {
        if ((teachers == null || teachers.isEmpty()) &&
                (rooms == null || rooms.isEmpty()) &&
                (groups == null || groups.isEmpty())) {
            return "At least one of parameters 'teachers', 'ŗooms' or 'groups' must contain valid value";
        } else return "";
    }
}
