package lv.tsi.calendar.service.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SearchBean {

    public enum SearchField { TEACHER, GROUP, ALL, ROOM }

    private Map<SearchField, Set<String>> searchTerms = new HashMap<>();
    private Map<SearchField, Set<String>> excludeTerms = new HashMap<>();

    public Map<SearchField, Set<String>> getSearchTerms() {
        return searchTerms;
    }

    public Map<SearchField, Set<String>> getExcludeTerms() {
        return excludeTerms;
    }

    public void addSearchTerm(SearchField searchField, String searchTerm) {
        Set<String> terms = searchTerms.get(searchField);
        if (terms != null) {
            terms.add(searchTerm);
        } else {
            HashSet<String> valueSet = new HashSet<>();
            valueSet.add(searchTerm);
            searchTerms.put(searchField, valueSet);
        }
    }

    public void addExcludeTerm(SearchField searchField, String excludeTerm) {
        Set<String> terms = excludeTerms.get(searchField);
        if (terms != null) {
            terms.add(excludeTerm);
        } else {
            HashSet<String> valueSet = new HashSet<>();
            valueSet.add(excludeTerm);
            excludeTerms.put(searchField, valueSet);
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
