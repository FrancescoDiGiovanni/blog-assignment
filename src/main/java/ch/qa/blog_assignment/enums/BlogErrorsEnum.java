package ch.qa.blog_assignment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BlogErrorsEnum {
    ROLE_NOT_AUTHORIZED("ERR001", "Unauthorized user role", HttpStatus.FORBIDDEN),
    POST_NOT_FOUND("ERR002", "Unable to find posts with id", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("ERR003", "There are some problems on user inputs.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String description;
    private final HttpStatus status;
}
