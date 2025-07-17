package com.belejki.belejki.restful.shared.aop;

import com.belejki.belejki.restful.shared.exception.ErrorResponse;
import com.belejki.belejki.restful.shared.exception.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler {


	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT.value());

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

}
