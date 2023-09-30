package com.rntgroup.tstapp.repository;

public class UserTestRepositoryException extends RuntimeException {
	public UserTestRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserTestRepositoryException(String message) {
		super(message);
	}
}
