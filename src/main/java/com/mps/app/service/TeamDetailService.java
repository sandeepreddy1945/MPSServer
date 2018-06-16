/**
 * 
 */
package com.mps.app.service;

import java.util.List;

import com.mps.app.model.entities.TeamDetailsBoundary;

/**
 * @author Sandeep
 *
 */
public interface TeamDetailService {

	public TeamDetailsBoundary addNewTeam(TeamDetailsBoundary m);

	public boolean deleteTeam(TeamDetailsBoundary m);

	public boolean deleteTeam(String teamName);

	public List<TeamDetailsBoundary> listAllTeam();

	public void updateTeam(TeamDetailsBoundary m);
}
