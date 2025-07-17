package com.belejki.belejki.restful.shared.aop;

import com.belejki.belejki.restful.shared.exception.EmptyOrDigitIngridientException;
import com.belejki.belejki.restful.shared.exception.ErrorResponse;
import com.belejki.belejki.restful.shared.exception.NullIngridientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeRestExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NullIngridientException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NO_CONTENT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmptyOrDigitIngridientException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.PARTIAL_CONTENT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.PARTIAL_CONTENT);
    }

}
