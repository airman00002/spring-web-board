package com.blogbackend.blogbackend.service;

import com.blogbackend.blogbackend.controller.request.UserRequest;
import com.blogbackend.blogbackend.model.User;

public interface UserService {

	User register(UserRequest userRequest);
	
	User findUSerByUsername(String username);
	
}
