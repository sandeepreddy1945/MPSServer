package com.mps.app.login;

import java.io.Serializable;

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
@JsonPropertyOrder({ "id", "name" })
@JsonInclude(value = Include.NON_NULL)
public class Authority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5216272878670103048L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

}
