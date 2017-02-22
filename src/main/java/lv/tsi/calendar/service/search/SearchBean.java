package lv.tsi.calendar.service.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lv.tsi.calendar.exceptions.ParameterValidationException;

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

    public void addSearchTerm(SearchField searchField, String searchTerm) {
        if (searchTerm != null && searchTerm.length() > 2) {
            searchTerms.get(searchField).add(searchTerm);
        }
    }

    public void addExcludeTerm(SearchField searchField, String excludeTerm) {
        if (excludeTerm != null && excludeTerm.length() > 2) {
            excludeTerms.get(searchField).add(excludeTerm);
        }
    }

    public Map<SearchField, Set<String>> getSearchTerms() {
        return searchTerms;
    }

    public Map<SearchField, Set<String>> getExcludeTerms() {
        return excludeTerms;
    }

    @JsonIgnore
    public Set<String> getSearchTerms(SearchField searchField) {
        return searchTerms.get(searchField);
    }

    @JsonIgnore
    public Set<String> getExcludeTerms(SearchField searchField) {
        return excludeTerms.get(searchField);
    }

    public void validateSearchBean() {
        if (searchTerms.get(SearchField.TEACHER).isEmpty()
                && searchTerms.get(SearchField.GROUP).isEmpty()
                && searchTerms.get(SearchField.ROOM).isEmpty()
                && searchTerms.get(SearchField.ALL).isEmpty()) {
            throw new ParameterValidationException("You must provide valid search query. Search term must be at least 3 symbols long to be accepted.\n" +
                    "Example: '/search?q=t:teacher -room:210'");
        }
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "searchTerms=" + searchTerms +
                ", excludeTerms=" + excludeTerms +
                '}';
    }
}
