package lv.tsi.schedule.exceptions;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    public static final String ERROR_MESSAGE = "Oops! Something went wrong. Please try again later.";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        // Otherwise setup and send the user to a default error-view.
        return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ParameterValidationException.class)
    public ResponseEntity<String> validationExceptionHandler(ParameterValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}