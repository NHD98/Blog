package com.petproject.blog.service;

import java.text.MessageFormat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petproject.blog.dto.JwtRequest;
import com.petproject.blog.dto.UserRegister;
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

	public int register(UserRegister userRegister) {

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setEmail(userRegister.getEmail());
		user.setFullName(userRegister.getFullName());
		user.setPassword(encoder.encode(userRegister.getPassword()));
		user.setRoles("USER");
		user.setUsername(userRegister.getUsername());
		System.out.println(user.getPassword());

		int result = userRepository.register(user.getUsername(), user.getPassword(), user.isStatus(), user.getEmail(),
				user.getFullName(), user.getRoles(), user.isExpired(), user.isLocked(), user.isEnable());
		return result;
	}
}
