package com.blogbackend.blogbackend.exception;

public class BlogNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlogNotFoundException(Long id) {
		super("Could not find blog : " + id);
	}

	public BlogNotFoundException(String name) {
		super("Could not find blog : " + name);
	}
}
