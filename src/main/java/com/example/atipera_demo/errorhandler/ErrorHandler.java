package com.example.atipera_demo.errorhandler;

import com.example.atipera_demo.dto.ErrorResponse;
import com.example.atipera_demo.exception.UnexpectedException;
import com.example.atipera_demo.exception.UserNotFoundException;
import com.example.atipera_demo.exception.XmlHeaderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundHandler(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(XmlHeaderException.class)
    public ResponseEntity<ErrorResponse> xmlHeaderHandler(XmlHeaderException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorResponse> unexpectedErrorHandler(UnexpectedException e) {
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorResponse(HttpStatus.I_AM_A_TEAPOT.value(), e.getMessage()));
    }
}
