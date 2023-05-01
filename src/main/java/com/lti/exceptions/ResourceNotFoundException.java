package com.lti.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
//we need to create global exception class to catch all the exceptions