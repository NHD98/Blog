package com.petproject.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petproject.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM user WHERE id = :id", nativeQuery = true)
	public Optional<User> findById(@Param("id") int id);

	@Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
	public Optional<User> findByUsername(@Param("username") String username);

	@Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
	public Optional<User> findByEmail(@Param("email") String email);

	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query(value = "insert into user(username, password, status, email, full_name, roles, expired, locked, enable) values (:username, :password, :status, :email, :fullName, :roles, :expired, :locked, :enable)", nativeQuery = true)
	public int register(@Param("username") String username, @Param("password") String password,
			@Param("status") boolean status, @Param("email") String email, @Param("fullName") String fullName,
			@Param("roles") String roles, @Param("expired") boolean expired, @Param("locked") boolean locked,
			@Param("enable") boolean enable);

	@Query(value = "SELECT id FROM user WHERE username = :username", nativeQuery = true)
	public long getIdByUsername(@Param("username") String username);
}
