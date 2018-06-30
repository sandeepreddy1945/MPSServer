/**
 * 
 */
package com.mps.app.teamdetails;

import java.util.List;

/**
 * @author Sandeep
 *
 */
public interface TeamDetailDAO {

	public TeamDetailsBoundary addNewTeam(TeamDetailsBoundary m);

	public boolean deleteTeam(TeamDetailsBoundary m);

	public boolean deleteTeam(String teamName);

	public List<TeamDetailsBoundary> listAllTeam();

	public void updateTeam(TeamDetailsBoundary oldM, TeamDetailsBoundary newM);

}
