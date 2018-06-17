/*package com.mps.app.model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({})
@Audited
@Entity
@Table(name = "UserLoginTab")
public class UserLoginDetails implements UserDetails {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 8356819781141672372L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userLoginId;

	private String userName;
	private String passWord;
	private boolean expired;
	private boolean locked;
	private boolean enabled;
	private boolean credenialsExpired;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// return expired;
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// return locked;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// return credenialsExpired;
		return true;
	}

	@Override
	public boolean isEnabled() {
		// return enabled;
		return true;
	}

}
*/