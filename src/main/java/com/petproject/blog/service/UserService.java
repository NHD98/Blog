package com.petproject.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petproject.blog.model.User;
import com.petproject.blog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
	
	public long getIdByUsername(String username) {
		return userRepository.getIdByUsername(username);
	}
}
