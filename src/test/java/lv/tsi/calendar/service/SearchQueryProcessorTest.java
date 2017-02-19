package lv.tsi.calendar.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class SearchQueryProcessorTest {
    @Test
    public void testSearchBean() throws Exception {
        SearchQueryProcessor processor = new SearchQueryProcessor();
        SearchBean searchBean = processor.createSearchBean("asgfasdf -tree teacher:'Jānis Liepa' -t:Buzdin -g:\"группа а\" ");
    }

}