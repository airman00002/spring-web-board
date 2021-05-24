package com.blogbackend.blogbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class Blog {

	public @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	@Column(length = 255,nullable = false,unique = false)
	public String name;
	public String image;
	public String description;
}
