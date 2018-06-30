package com.mps.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Sandeep Reddy Battula
 *
 */
@Getter
@Setter
@ToString(includeFields = true)
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "access_token", "token_type", "refresh_token", "expires_in", "scope", "organization", "jti",
		"error", "error_description" })
public class OAuthBoundary implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8948303462439240005L;

	@JsonProperty("access_token")
	private String access_token;
	@JsonProperty("token_type")
	private String token_type;
	@JsonProperty("refresh_token")
	private String refresh_token;
	@JsonProperty("expires_in")
	private long expires_in;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("organization")
	private String organization;
	@JsonProperty("jti")
	private String jti;
	@JsonProperty("error")
	private String error;
	@JsonProperty("error_description")
	private String error_description;

}
