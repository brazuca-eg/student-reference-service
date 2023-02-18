package com.nure.kravchenko.student.reference.exception.handler;

import com.nure.kravchenko.student.reference.exception.ErrorResponse;
import com.nure.kravchenko.student.reference.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public final class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode(status.name()).errorDescription(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
