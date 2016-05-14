package lv.tsi.schedule.exceptions;

public class ParameterValidationException extends RuntimeException {
    public ParameterValidationException(String validationErrors) {
        super(validationErrors);
    }
}
