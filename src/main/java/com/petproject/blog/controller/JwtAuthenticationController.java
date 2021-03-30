package com.petproject.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.petproject.blog.dto.JwtRequest;
import com.petproject.blog.dto.UserRegister;
import com.petproject.blog.security.JwtTokenUtil;
import com.petproject.blog.service.JwtUserDetailsService;
import com.petproject.blog.service.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
		ResponseEntity<String> response;
		if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
			response = new ResponseEntity<String>("Passwords are not match", HttpStatus.BAD_REQUEST);
		} else if (userService.getUserByUsername(userRegister.getUsername()) != null || userService.getUserByEmail(userRegister.getEmail()) != null) {
			response = new ResponseEntity<String>("Username or email existed", HttpStatus.BAD_REQUEST);
		} else {
			int result = userDetailsService.register(userRegister);
			response = result > 0 ? new ResponseEntity<String>("New User is created", HttpStatus.OK)
					: new ResponseEntity<String>("Error on creating new User", HttpStatus.BAD_REQUEST);
		}
		return response;

	}
}