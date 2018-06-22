package com.mps.app.model;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import groovy.transform.ToString;
import lombok.Getter;

/**
 * 
 * @author Sandeep Reddy Battula
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "email", "password", "rememberMe" })
public class AuthBoundary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713958307641661819L;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("rememberMe")
	private boolean rememberMe;

}
