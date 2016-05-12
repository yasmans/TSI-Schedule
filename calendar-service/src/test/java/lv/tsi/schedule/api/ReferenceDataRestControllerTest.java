package lv.tsi.schedule.api;

import lv.tsi.schedule.service.DataService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceDataRestControllerTest {

    private ReferenceDataRestController controller;
    @Mock
    private DataService dataService;


    @Before
    public void setUp() throws Exception {
        controller = new ReferenceDataRestController();
        controller.setDataService(dataService);
        when(dataService.getReferenceData(any(String.class), any())).thenReturn(null);
    }

    @Test
    public void testGetReferenceData() throws Exception {
        assertNotNull(controller);
        assertNull(controller.getReferenceData("lv", new String[]{"room", "teachers"}));
    }
}