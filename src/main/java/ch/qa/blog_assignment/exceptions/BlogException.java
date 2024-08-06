package ch.qa.blog_assignment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String responseMessage;
    private final String code;

    public BlogException(String code, String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.responseMessage = "";
        this.code = code;

    }

    public BlogException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.responseMessage = "";
        this.code = code;
    }
}
