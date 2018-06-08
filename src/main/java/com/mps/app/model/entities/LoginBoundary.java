/**
 * 
 */
package com.mps.app.model.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
 * @author Sandeep Reddy Battula
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "loginId", "email", "password", "rememberMe", "registerBoundary" })
@Entity
@Audited
@Table(name = "Login", indexes = { @Index(columnList = "loginId") })
@NamedQueries(value = {
		@NamedQuery(name = "@ValidateLogin", query = "from LoginBoundary l where lower(l.email) = :email and l.password= :pass") })
public class LoginBoundary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132387671223807642L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("loginId")
	private long loginId;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("rememberMe")
	private boolean rememberMe;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "registerId")
	@JsonProperty("registerBoundary")
	private RegisterBoundary registerBoundary;
}
