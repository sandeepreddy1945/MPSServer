package com.mps.app.login;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Sandeep
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "registerId", "terms", "registerId", "fullName", "email", "password", "confirmPassword" })
@Entity
@Audited
@Table(name = "Register", indexes = { @Index(columnList = "registerId") })
@NamedQueries(value = { @NamedQuery(name = "@ListAllUsers", query = "from RegisterBoundary r"),
		@NamedQuery(name = "@CheckForUser", query = "from RegisterBoundary r where lower(r.email) = :email") })
public class RegisterBoundary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2538948833335188935L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("registerId")
	private long registerId;
	@JsonProperty("fullName")
	private String fullName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("confirmPassword")
	private String confirmPassword;
	@JsonProperty("terms")
	private boolean terms;
}
