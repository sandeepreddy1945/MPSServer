package com.mps.app.login;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Exposed API from MPS OAuth2 Server.
 * 
 * @author Sandeep Reddy Battula
 *
 */
@Getter
@Setter
@ToString
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "id", "username", "password", "accountExpired", "accountLocked", "credentialsExpired", "enabled",
		"authorities" })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6732685477673143275L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("accountExpired")
	private boolean accountExpired;

	@JsonProperty("accountLocked")
	private boolean accountLocked;

	@JsonProperty("credentialsExpired")
	private boolean credentialsExpired;

	@JsonProperty("enabled")
	private boolean enabled;

	@JsonProperty("authorities")
	private List<Authority> authorities;

}
