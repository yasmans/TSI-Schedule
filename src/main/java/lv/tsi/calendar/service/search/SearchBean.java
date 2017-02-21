package lv.tsi.calendar.service.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SearchBean {

    public enum SearchField { TEACHER, GROUP, ALL, ROOM }

    private Map<SearchField, Set<String>> searchTerms;
    private Map<SearchField, Set<String>> excludeTerms;

    public SearchBean() {
        searchTerms = new HashMap<>();
        searchTerms.put(SearchField.ALL, new HashSet<>());
        searchTerms.put(SearchField.GROUP, new HashSet<>());
        searchTerms.put(SearchField.TEACHER, new HashSet<>());
        searchTerms.put(SearchField.ROOM, new HashSet<>());
        excludeTerms = new HashMap<>();
        excludeTerms.put(SearchField.ALL, new HashSet<>());
        excludeTerms.put(SearchField.GROUP, new HashSet<>());
        excludeTerms.put(SearchField.TEACHER, new HashSet<>());
        excludeTerms.put(SearchField.ROOM, new HashSet<>());
    }

    public Map<SearchField, Set<String>> getSearchTerms() {
        return searchTerms;
    }

    public Map<SearchField, Set<String>> getExcludeTerms() {
        return excludeTerms;
    }

    public void addSearchTerm(SearchField searchField, String searchTerm) {
        searchTerms.get(searchField).add(searchTerm);
    }

    public void addExcludeTerm(SearchField searchField, String excludeTerm) {
        excludeTerms.get(searchField).add(excludeTerm);
    }

    @JsonIgnore
    public Set<String> getGroupSearchTerms() {
        Set<String> groupSearchTerms = new HashSet<>();
        groupSearchTerms.addAll(searchTerms.get(SearchField.GROUP));
        groupSearchTerms.addAll(searchTerms.get(SearchField.ALL));
        return groupSearchTerms;
    }

    @JsonIgnore
    public Set<String> getTeacherSearchTerms() {
        Set<String> teacherSearchTerms = new HashSet<>();
        teacherSearchTerms.addAll(searchTerms.get(SearchField.TEACHER));
        teacherSearchTerms.addAll(searchTerms.get(SearchField.ALL));
        return teacherSearchTerms;
    }

    @JsonIgnore
    public Set<String> getRoomSearchTerms() {
        Set<String> roomSearchTerms = new HashSet<>();
        roomSearchTerms.addAll(searchTerms.get(SearchField.ROOM));
        roomSearchTerms.addAll(searchTerms.get(SearchField.ALL));
        return roomSearchTerms;
    }

    @JsonIgnore
    public Set<String> getExcludeTerms(SearchField searchField) {
        return excludeTerms.get(searchField);
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "searchTerms=" + searchTerms +
                ", excludeTerms=" + excludeTerms +
                '}';
    }
}
