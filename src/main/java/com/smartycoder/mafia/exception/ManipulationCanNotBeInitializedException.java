package com.smartycoder.mafia.exception;

public class ManipulationCanNotBeInitializedException extends RuntimeException {

	public ManipulationCanNotBeInitializedException(String message) {
		super(message);
	}

	public ManipulationCanNotBeInitializedException(String message, Throwable cause) {
		super(message, cause);
	}
}