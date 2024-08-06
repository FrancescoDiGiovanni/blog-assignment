package ch.qa.blog_assignment.handlers;

import ch.qa.blog_assignment.exceptions.BlogException;
import ch.qa.blog_assignment.utilities.Response;
import ch.qa.blog_assignment.utilities.ResponseUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BlogExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public <T> ResponseEntity<Response<T>> handleServiceExceptions(
            BlogException blogException) {
        return ResponseUtility.buildErrorResponseEntityFromBlogException(blogException, log);
    }
}
