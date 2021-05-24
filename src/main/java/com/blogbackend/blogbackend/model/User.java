package com.blogbackend.blogbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "`user`")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(nullable = false, unique = true)
	public String username;

	@Column(nullable = false)
	public String firstName;

	@Column(nullable = false)
	public String lastName;
	@Column(nullable = false)
	public String email;

	@Column(nullable = false, unique = true)
	public String password;

	//* การให้สิทธิ์ ต้องไม่ว่าง
		@Column(nullable = false)
		private String role;
}

