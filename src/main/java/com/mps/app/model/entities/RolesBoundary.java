package com.mps.app.model.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "roleId", "role", "loginBoundary" })
@Entity
@Audited
@Table(name = "RolesTab", indexes = { @Index(columnList = "roleId") })
@NamedQueries({ @NamedQuery(name = "@checkRole", query = "from RolesBoundary r where r.loginBoundary = :loginId") })
public class RolesBoundary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875617945916234209L;

	@JsonProperty("roleId")
	private long roleId;
	@JsonProperty("role")
	private String role;
	@JsonProperty("loginBoundary")
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "loginId")
	private LoginBoundary loginBoundary;
}
