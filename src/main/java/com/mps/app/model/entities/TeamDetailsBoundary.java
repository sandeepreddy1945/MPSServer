/**
 * 
 */
package com.mps.app.model.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Sandeep
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "teamDetailId", "teamName", "managerPortalId", "managerPortalId", "managerDetailsBoundary" })
@Entity
@Audited
@Table(name = "TeamDetailsTab", indexes = { @Index(columnList = "teamDetailId") })
@NamedQueries({ @NamedQuery(name = "@lisAllTeams", query = "from TeamDetailsBoundary m"),
		@NamedQuery(name = "@deleteTeamByName", query = "delete from TeamDetailsBoundary m where m.teamName = :teamName"),
		@NamedQuery(name = "@updateTeamDetails", query = "update TeamDetailsBoundary m set m.teamName = :teamName, "
				+ " m.managerPortalId = :managerPortalId, m.projectName = :projectName ") })
public class TeamDetailsBoundary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3862233480311043563L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("teamDetailId")
	private long teamDetailId;
	@Column(unique = true, nullable = false)
	@JsonProperty("teamName")
	private String teamName;
	@JsonProperty("managerPortalId")
	private String managerPortalId;
	@JsonProperty("projectName")
	private String projectName;
	@JsonProperty("managerDetailsBoundary")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "managerId")
	private ManagerDetailsBoundary managerDetailsBoundary;

}
