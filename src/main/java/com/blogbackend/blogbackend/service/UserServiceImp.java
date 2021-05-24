package com.blogbackend.blogbackend.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogbackend.blogbackend.controller.request.UserRequest;
import com.blogbackend.blogbackend.exception.UserDupicateException;
import com.blogbackend.blogbackend.model.User;
import com.blogbackend.blogbackend.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImp(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	}

	@Override
	public User register(UserRequest userRequest) {
		User user = userRepository.findByUsername(userRequest.getUsername());
		if (user == null) {
			user = new User().setUsername(userRequest.getUsername())
							.setFirstName(userRequest.getFirstName())
							.setLastName(userRequest.getLastName())
							.setEmail(userRequest.getEmail())
							.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
							.setRole(userRequest.getRole());
			return userRepository.save(user);
		}
		throw new UserDupicateException(userRequest.getUsername());
	}

	@Override
	public User findUSerByUsername(String username) {
		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
}
