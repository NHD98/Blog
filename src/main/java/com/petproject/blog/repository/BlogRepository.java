package com.petproject.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petproject.blog.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

	@Query(value = "SELECT * FROM blog WHERE author_id = :author_id", nativeQuery = true)
	public List<Blog> findAllByAuthor(@Param("author_id") long authorId);

	@Modifying
	@Transactional(rollbackOn = Exception.class)
	@Query(value = "INSERT INTO blog(title, content, author_id, created_at, category) VALUES (:title, :content, :author_id, :created_at, :category)", nativeQuery = true)
	public int createBlog(@Param("title") String title, @Param("content") String content,
			@Param("author_id") long authorId, @Param("created_at") LocalDateTime createdAt,
			@Param("category") String category);
}
