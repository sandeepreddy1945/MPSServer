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
import javax.persistence.OneToMany;
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
@JsonPropertyOrder({ "managerId", "portalId", "name", "teamName", "designation", "experience", "email",
		"teamDetailsBoundary" })
@Entity
@Audited
@Table(name = "MemberDetailsTab", indexes = { @Index(columnList = "managerId"), @Index(columnList = "portalId") })
@NamedQueries({ @NamedQuery(name = "@listAllManagers", query = "from ManagerDetailsBoundary m"),
		@NamedQuery(name = "@deleteManagerByPortal", query = "delete from ManagerDetailsBoundary m where m.portalId = :portalId") })
public class ManagerDetailsBoundary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160103817340940259L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("managerId")
	private long managerId;
	@JsonProperty("portalId")
	private String portalId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("teamName")
	private String teamName;
	@JsonProperty("designation")
	private String designation;
	@JsonProperty("experience")
	private String experience;
	@JsonProperty("email")
	private String email;
	@JsonProperty("teamDetailsBoundary")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "teamDetailId")
	private TeamDetailsBoundary teamDetailsBoundary;

}
