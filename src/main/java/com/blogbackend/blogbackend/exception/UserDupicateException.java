package com.blogbackend.blogbackend.exception;

public class UserDupicateException extends RuntimeException {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public UserDupicateException(String username) {
	super("Username : "+username + " already exists !!");
}
}
