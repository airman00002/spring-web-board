package com.blogbackend.blogbackend.controller.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogRequest {

	@NotEmpty(message = "String is Empty !!")
	public String name;
	public String image;
	public String description;
	
}
