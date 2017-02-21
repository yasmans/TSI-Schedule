package lv.tsi.calendar.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
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

    public long getCurrentTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public long getStartOfPreviousMonth() {
        return LocalDateTime
                .now()
                .minusMonths(1)
                .withDayOfMonth(1)
                .truncatedTo(ChronoUnit.DAYS)
                .toInstant(ZoneOffset.UTC)
                .getLong(ChronoField.INSTANT_SECONDS);
    }

    public long getStartOfMonthAfterHalfAYear() {
        return LocalDateTime
                .now()
                .minusMonths(1)
                .withDayOfMonth(1)
                .plusDays(182)
                .truncatedTo(ChronoUnit.DAYS)
                .toInstant(ZoneOffset.UTC)
                .getLong(ChronoField.INSTANT_SECONDS);
    }
}
