package com.petproject.blog.controller;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.petproject.blog.dto.BlogDTO;
import com.petproject.blog.model.Blog;
import com.petproject.blog.security.JwtTokenUtil;
import com.petproject.blog.service.BlogService;
import com.petproject.blog.service.UserService;

@RestController("/blog")
public class BlogController {

	@Autowired
	private JwtTokenUtil tokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@PostMapping("/create")
	public ResponseEntity<Integer> createBlog(@RequestBody BlogDTO blog, @RequestHeader("Authorization") String token) {
		String username = tokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
		long authorId = userService.getIdByUsername(username);
		LocalDateTime createdAt = LocalDateTime.now();

		Blog newBlog = new Blog();
		newBlog.setTitle(blog.getTitle());
		newBlog.setContent(blog.getContent());
		newBlog.setAuthorId(authorId);
		newBlog.setCreatedAt(createdAt);
		newBlog.setCategory(blog.getCategory());

		int result = blogService.createBlog(newBlog);

		ResponseEntity<Integer> response;

		if (result > 0) {
			response = new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
}
