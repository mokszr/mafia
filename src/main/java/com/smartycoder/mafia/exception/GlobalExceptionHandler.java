package com.smartycoder.mafia.exception;

import java.util.Locale;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.smartycoder.mafia.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private MessageSource messageSource;

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraint(Exception ex, Locale locale) {
		
		HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setStatus(httpStatus);
		errorResponse.setMessage(messageSource.getMessage("globalexception.constraint_validation", null, locale));
		errorResponse.setErrorCode(String.valueOf(httpStatus.value()));
		
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}
	
	@ExceptionHandler(value = { MafiaJobNotFinishedException.class })
	public ResponseEntity<Object> handleMafiaJobNotFinishedException(Exception ex) {
		
		return returnErrorResponse(ex);
	}

	
	@ExceptionHandler(value = { MafiaJobNotFoundException.class })
	public ResponseEntity<Object> handleMafiaJobNotFoundException(Exception ex) {
		
		return returnErrorResponse(ex);
	}
	
	@ExceptionHandler(value = { ManipulationCanNotBeInitializedException.class })
	public ResponseEntity<Object> handleManipulationCanNotBeInitializedException(Exception ex) {
		
		return returnErrorResponse(ex);
	}
	
	@ExceptionHandler(value = { ManipulationNotFoundException.class })
	public ResponseEntity<Object> handleManipulationNotFoundException(Exception ex) {
		
		return returnErrorResponse(ex);
	}
	
	@ExceptionHandler(value = { StorageException.class })
	public ResponseEntity<Object> handleStorageException(Exception ex) {
		
		return returnErrorResponse(ex);
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private ResponseEntity<Object> returnErrorResponse(Exception ex) {
		HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setStatus(httpStatus);
		errorResponse.setMessage(ex.getClass().getSimpleName() + " " + ex.getMessage());
		errorResponse.setErrorCode(String.valueOf(httpStatus.value()));
		
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}
	 
}
