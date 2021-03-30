package com.petproject.blog.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String content;
	@Column(name = "author_id")
	private long authorId;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	private String category;
}
