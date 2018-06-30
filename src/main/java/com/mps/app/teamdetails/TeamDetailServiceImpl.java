package com.mps.app.teamdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Sandeep
 *
 */
@Service
public class TeamDetailServiceImpl implements TeamDetailService {

	@Autowired
	private TeamDetailDAO teamDAO;

	@Override
	public TeamDetailsBoundary addNewTeam(TeamDetailsBoundary m) {

		return teamDAO.addNewTeam(m);
	}

	@Override
	public boolean deleteTeam(TeamDetailsBoundary m) {

		return teamDAO.deleteTeam(m);
	}

	@Override
	public boolean deleteTeam(String teamName) {

		return teamDAO.deleteTeam(teamName);
	}

	@Override
	public List<TeamDetailsBoundary> listAllTeam() {

		return teamDAO.listAllTeam();
	}

	@Override
	public void updateTeam(TeamDetailsBoundary oldM, TeamDetailsBoundary newM) {

		teamDAO.updateTeam(oldM, newM);
	}

}
