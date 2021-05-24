package com.blogbackend.blogbackend.controller.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

	
	@NotEmpty
	public String username;
	
	@NotEmpty
	public String firstName;
	
	@NotEmpty
	public String lastName;
	
	@NotEmpty
	public String email;
	
	
	@NotEmpty
	@Length(min = 6,message ="The field must be at least {min} charecters ")
	public String password;
	
	@NotEmpty
	public String role="admin";
	
}
