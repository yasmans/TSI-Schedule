package lv.tsi.schedule.domain.params;

import lv.tsi.schedule.exceptions.ParameterValidationException;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class URLDateParam implements Serializable {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date;

    public URLDateParam(String dateParam) {
        try {
            this.date = dateFormat.parse(dateParam);
        } catch (ParseException e) {
            throw new ParameterValidationException("Cannot parse '" + dateParam + "' expected date format: 'yyyy-MM-dd");
        }
    }

    public Date getDate() {
        return date;
    }
}
