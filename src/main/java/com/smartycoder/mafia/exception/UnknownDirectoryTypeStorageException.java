package com.smartycoder.mafia.exception;

public class UnknownDirectoryTypeStorageException extends RuntimeException {

	public UnknownDirectoryTypeStorageException(String message) {
		super(message);
	}

	public UnknownDirectoryTypeStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}