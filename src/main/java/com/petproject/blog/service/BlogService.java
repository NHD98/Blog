package com.petproject.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petproject.blog.model.Blog;
import com.petproject.blog.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public Blog getBlogById(long id) {
		return blogRepository.findById(id).get();
	}

	public List<Blog> getBlogByAuthor(long authorId) {
		return blogRepository.findAllByAuthor(authorId);
	}

	public int createBlog(Blog blog) {
		return blogRepository.createBlog(blog.getTitle(), blog.getContent(), blog.getAuthorId(), blog.getCreatedAt(),
				blog.getCategory());
	}
}
