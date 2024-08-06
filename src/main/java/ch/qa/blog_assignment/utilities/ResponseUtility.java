package ch.qa.blog_assignment.utilities;

import ch.qa.blog_assignment.exceptions.BlogException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtility {
    private static final String ERROR_CODE_MESSAGE_FORMAT = "{}: {}";

    public static <T> ResponseEntity<Response<T>> buildSuccessResponseEntity(String message, T payload, Logger log) {
        log.info(message);
        Response<T> response = new Response<>();
        return new ResponseEntity<>(response.buildResponse(true, message, payload), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> buildErrorResponseEntity(String code, HttpStatus httpStatus, String errorMessage, T payload, Logger log) {
        log.info(ERROR_CODE_MESSAGE_FORMAT, code, errorMessage);
        Response<T> response = new Response<>();
        return new ResponseEntity<>(response.buildErrorResponse(code, errorMessage, payload),
                httpStatus);
    }

    public static <T> ResponseEntity<Response<T>> buildErrorResponseEntityFromBlogException(BlogException blogException, Logger log) {
        log.error(ERROR_CODE_MESSAGE_FORMAT, blogException.getCode(), blogException.getMessage(), blogException);
        Response<T> response = new Response<>();
        return new ResponseEntity<>(
                response.buildErrorResponse(blogException.getCode(), blogException.getMessage()),
                blogException.getHttpStatus()
        );
    }
}
