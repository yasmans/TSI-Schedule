package lv.tsi.schedule.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


/**
 * Application time service
 *
 *  Use this class to get current time and calendar.
 *  If date objects are created manually, code will become untestable.
 */
@Service
public class ApplicationTimeService {

    private Calendar calendar = Calendar.getInstance();

    public long getCurrentTimestamp() {
        return calendar.getTimeInMillis();
    }

    public Date getCurrentDate() {
        return calendar.getTime();
    }

}
