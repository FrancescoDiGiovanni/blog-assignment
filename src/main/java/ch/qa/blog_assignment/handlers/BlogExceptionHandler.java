package ch.qa.blog_assignment.handlers;

import ch.qa.blog_assignment.enums.BlogErrorsEnum;
import ch.qa.blog_assignment.exceptions.BlogException;
import ch.qa.blog_assignment.utilities.Response;
import ch.qa.blog_assignment.utilities.ResponseUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BlogExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public <T> ResponseEntity<Response<T>> handleServiceExceptions(
            BlogException blogException) {
        return ResponseUtility.buildErrorResponseEntityFromBlogException(blogException, log);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseUtility.buildErrorResponseEntity(BlogErrorsEnum.VALIDATION_ERROR.getCode(), BlogErrorsEnum.VALIDATION_ERROR.getStatus(), BlogErrorsEnum.VALIDATION_ERROR.getDescription(), errors, log);
    }
}
