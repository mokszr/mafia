package com.smartycoder.mafia.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDto {

    private HttpStatus status;
    
    private String errorCode;
    
    private String message;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
  
}