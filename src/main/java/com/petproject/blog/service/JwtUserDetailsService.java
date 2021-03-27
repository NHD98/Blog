package com.petproject.blog.service;

import java.text.MessageFormat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petproject.blog.dto.JwtRequest;
import com.petproject.blog.model.User;
import com.petproject.blog.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);

		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UsernameNotFoundException(
					MessageFormat.format("User with username {0} cannot be found.", username));
		}
	}

	public int register(User user) {
		int result = userRepository.register(user.getUsername(), user.getPassword(), user.isStatus(), user.getEmail(),
				user.getFullName(), user.getRoles());
		return result;
	}

	public String login(JwtRequest userLogin) {
		return null;
	}

}
