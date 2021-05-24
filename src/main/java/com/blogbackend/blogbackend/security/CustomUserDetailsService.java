package com.blogbackend.blogbackend.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogbackend.blogbackend.model.User;
import com.blogbackend.blogbackend.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUSerByUsername(username);
		if (user != null) {

			// *ดึงสิทธิ์
			Set<GrantedAuthority> roles = new HashSet<>();
			roles.add(new SimpleGrantedAuthority(user.getRole())); // *สร้างมา เก็บ roles// getFirstName

			// *สิทธิ์ แปลงเป็น list
			List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>(roles);

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authority);
		}else {
			throw new UsernameNotFoundException("Username : " + username + " does not exist !!");
		}
		
	}
}
