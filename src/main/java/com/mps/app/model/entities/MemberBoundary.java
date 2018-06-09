/**
 * 
 */
package com.mps.app.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
 * @author Sandeep
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "memberId", "portalId", "fullName", "email", "designation", "isManager", "imageStr",
		"managersPortalId", "experience" })
@Entity
@Audited
@Table(name = "MemberTab", indexes = { @Index(columnList = "memberId"), @Index(columnList = "portalId") })
@NamedQueries({ @NamedQuery(name = "@retriveList", query = "from MemberBoundary m"),
		@NamedQuery(name = "@updateMember", query = "update MemberBoundary m set m.portalId = :portalId, m.fullName = :fullName, "
				+ " m.email = :email, m.designation = :designation, m.experience = :experience where m.portalId = :oldPortalId"),
		@NamedQuery(name = "@searchMember", query = "from MemberBoundary m where m.portalId = :portalId") })
public class MemberBoundary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("memberId")
	private long memberId;
	@JsonProperty("portalId")
	@Column(unique = true, nullable = false)
	private String portalId;
	@JsonProperty("fullName")
	private String fullName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("designation")
	private String designation;
	@JsonProperty("isManager")
	private boolean isManager;
	@Lob
	@JsonProperty("imageStr")
	private String imageStr;
	@JsonProperty("managersPortalId")
	private String managersPortalId;
	@JsonProperty("experience")
	private double experience;

}
