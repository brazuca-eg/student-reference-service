package com.nure.kravchenko.student.reference.exception.handler;

import com.nure.kravchenko.student.reference.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public final class GlobalBaseExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode(status.name()).errorDescription(ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NureEmailException.class)
    public ResponseEntity<Object> handleNureEmailException(NureEmailException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode(status.name()).errorDescription(ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorValidationResponse errorResponse = ErrorValidationResponse.builder()
                .errorCode(status.name())
                .errorDescription(errorMap)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(InvalidProvidedDataException.class)
    public ResponseEntity<Object> handleInvalidArgument(InvalidProvidedDataException ex) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode(status.name()).errorDescription(ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

}
