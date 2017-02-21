package lv.tsi.calendar.service;

import lv.tsi.calendar.service.search.SearchBean;
import lv.tsi.calendar.service.search.SearchBean.SearchField;
import lv.tsi.calendar.service.search.SearchQueryProcessor;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SearchQueryProcessorTest {

    @Test
    public void testSearchBean() throws Exception {
        SearchQueryProcessor processor = new SearchQueryProcessor();
        SearchBean searchBean = processor.createSearchBean("4102BNL -eksāmens teacher:'Jānis Liepa' -r:310 -g:\"группа а\" t:'володя'");
        assertNotNull("Search bean should always exist", searchBean);

        Map<SearchField, Set<String>> searchTerms = searchBean.getSearchTerms();
        Map<SearchField, Set<String>> excludeTerms = searchBean.getExcludeTerms();

        Set<String> searchTermsAll = searchTerms.get(SearchField.ALL);
        assertThat(searchTermsAll, hasItems("4102BNL"));
        assertThat(searchTermsAll, hasSize(1));

        Set<String> searchItemsTeacher = searchTerms.get(SearchField.TEACHER);
        assertThat(searchItemsTeacher, hasItems("Jānis Liepa", "володя"));
        assertThat(searchItemsTeacher, hasSize(2));

        assertThat(searchTerms.get(SearchField.ROOM).size(), is(0));

        assertThat(searchTerms.get(SearchField.GROUP).size(), is(0));

        Set<String> excludeItemsAll = excludeTerms.get(SearchField.ALL);
        assertThat(excludeItemsAll, hasItems("eksāmens"));
        assertThat(excludeItemsAll, hasSize(1));

        Set<String> excludeItemsRoom = excludeTerms.get(SearchField.ROOM);
        assertThat(excludeItemsRoom, hasItems("310"));
        assertThat(excludeItemsRoom, hasSize(1));

        Set<String> excludeItemsGroup = excludeTerms.get(SearchField.GROUP);
        assertThat(excludeItemsGroup, hasItems("группа а"));
        assertThat(excludeItemsGroup, hasSize(1));

        assertThat(excludeTerms.get(SearchField.TEACHER).size(), is(0));
    }

    @Test
    public void testParsingSpecialSymbols() throws Exception {
        SearchQueryProcessor processor = new SearchQueryProcessor();
        SearchBean searchBean = processor.createSearchBean("'");
        assertThat("Invalid symbols should be ignored and no exception thrown", searchBean, notNullValue());
    }
}