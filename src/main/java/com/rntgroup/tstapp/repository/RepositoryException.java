package com.rntgroup.tstapp.repository;

public class RepositoryException extends RuntimeException {
	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepositoryException(String message) {
		super(message);
	}
}
