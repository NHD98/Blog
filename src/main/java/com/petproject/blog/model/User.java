package com.petproject.blog.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String email;
	private String username;
	private String password;
	@Column(name = "full_name")
	private String fullName;
	@Builder.Default
	private boolean status = true;
	private String roles;
	@Builder.Default
	private boolean expired = false;
	@Builder.Default
	private boolean locked = false;
	@Builder.Default
	private boolean enable = true;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roles);
		return Collections.singleton(simpleGrantedAuthority);
	}
	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enable;
	}
}
