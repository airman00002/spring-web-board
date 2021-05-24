package com.blogbackend.blogbackend.controller.api;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogbackend.blogbackend.controller.request.UserRequest;
import com.blogbackend.blogbackend.exception.ValidationException;
import com.blogbackend.blogbackend.model.User;
import com.blogbackend.blogbackend.service.UserService;


@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRequest useRequest,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError->{
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		return userService.register(useRequest);
	}
}
