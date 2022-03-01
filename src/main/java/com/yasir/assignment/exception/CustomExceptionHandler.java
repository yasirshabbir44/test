package com.yasir.assignment.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {CustomRuntimeException.class})
    public ResponseEntity handleCustomRuntimeException(CustomRuntimeException exception, WebRequest request){
        ErrorMessage errorMessage = buildErrorMessage(exception.getMessage(), request, exception.getHttpStatus());
        return new ResponseEntity(errorMessage, errorMessage.getHttpStatus());
    }

    private ErrorMessage buildErrorMessage(String exception, WebRequest request, HttpStatus detail){
        String path = request.getDescription(false).split("=")[1];
        return ErrorMessage.of().localDateTime(LocalDateTime.now())
                .message(exception)
                .path(path)
                .httpStatus(detail)
                .build();
    }
}
