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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Sandeep
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "memberId", "portalId", "fullName", "email", "designation", "isManager", "imageData",
		"managersPortalId", "experience", "employeeId", "gender", "comments", "hobbies", "month1score", "month2score",
		"month3score", "valueAddScore", "onQualityScore", "onTimeScore", "projectDetails", "teamName", "rating",
		"managerDetails" })
@Entity
@Audited
@Table(name = "MemberTab", indexes = { @Index(columnList = "memberId"), @Index(columnList = "portalId") })
@NamedQueries({ @NamedQuery(name = "@retriveList", query = "from MemberBoundary m"),
		@NamedQuery(name = "@updateMember", query = "update MemberBoundary m set m.portalId = :portalId, m.fullName = :fullName, "
				+ " m.email = :email, m.designation = :designation, m.experience = :experience where m.portalId = :oldPortalId"),
		@NamedQuery(name = "@searchMember", query = "from MemberBoundary m where m.portalId = :portalId"),
		@NamedQuery(name = "@deleteMember", query = "delete from MemberBoundary m where m.portalId = :portalId") })
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
	private String isManager;
	@Lob
	@JsonProperty("imageData")
	private String imageData;
	@JsonProperty("experience")
	private double experience;

	@JsonProperty("employeeId")
	private String employeeId;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("comments")
	private String comments;
	@JsonProperty("hobbies")
	private String hobbies;
	@JsonProperty("month1score")
	private double month1score;
	@JsonProperty("month2score")
	private double month2score;
	@JsonProperty("month3score")
	private double month3score;
	@JsonProperty("valueAddScore")
	private double valueAddScore;
	@JsonProperty("onQualityScore")
	private double onQualityScore;
	@JsonProperty("onTimeScore")
	private double onTimeScore;
	@JsonProperty("projectDetails")
	private String projectDetails;
	@JsonProperty("teamName")
	private String teamName;
	@JsonProperty("rating")
	private String rating;
	@JsonProperty("managerDetails")
	@OneToOne
	@JoinColumn(name = "managerId")
	private ManagerDetailsBoundary managerDetailsBoundary;

}
