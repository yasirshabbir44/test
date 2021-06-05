package com.az.assignment.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder(builderMethodName = "of")
public class ErrorMessage {

    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus httpStatus;
    private String path;
}