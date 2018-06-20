/**
 * 
 */
package com.mps.app.model.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@JsonPropertyOrder({ "loginId", "name", "email", "password", "rememberMe", "clientId", "imageData",
		"registerBoundary" })
@Entity
@Audited
@Table(name = "Login", indexes = { @Index(columnList = "loginId"), @Index(columnList = "email") })
@NamedQueries(value = {
		@NamedQuery(name = "@ValidateLogin", query = "from LoginBoundary l where lower(l.email) = :email and l.password= :pass"),
		@NamedQuery(name = "@CheckUserExistance", query = "from LoginBoundary l where lower(l.email) = :email"),
		@NamedQuery(name = "@resetPassword", query = "update LoginBoundary l set l.password = :pass where l.email = :email"),
		@NamedQuery(name = "@updateUserImage", query = "update LoginBoundary l set l.imageData = :imgData where l.email = :email") })
public class LoginBoundary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132387671223807642L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("loginId")
	private long loginId;
	@Column(nullable = false, unique = true)
	@JsonProperty("email")
	private String email;
	@JsonProperty("name")
	private String name;
	@JsonProperty("password")
	private String password;
	@JsonProperty("rememberMe")
	private boolean rememberMe;
	@JsonProperty("clientId")
	@Column(columnDefinition="default mpsguestuser")
	private String clientId;
	@Lob
	@Column(columnDefinition = "CLOB")
	@JsonProperty("imageData")
	private String imageData;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "registerId")
	@JsonProperty("registerBoundary")
	private RegisterBoundary registerBoundary;

}
