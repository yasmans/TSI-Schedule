package lv.tsi.calendar.exceptions;

public class ParameterValidationException extends RuntimeException {
    public ParameterValidationException(String validationErrors) {
        super(validationErrors);
    }
}
