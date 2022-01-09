package com.smartycoder.mafia.exception;

public class MafiaJobNotFoundException extends RuntimeException {

	public MafiaJobNotFoundException(String message) {
		super(message);
	}

	public MafiaJobNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}