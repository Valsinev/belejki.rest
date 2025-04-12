package com.belejki.belejki.restful.aop;

import com.belejki.belejki.restful.exception.EmptyOrDigitIngridientException;
import com.belejki.belejki.restful.exception.ErrorResponse;
import com.belejki.belejki.restful.exception.NullIngridientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeRestExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NullIngridientException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NO_CONTENT.value(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmptyOrDigitIngridientException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.PARTIAL_CONTENT.value(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.PARTIAL_CONTENT);
    }

    //edge cases
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
