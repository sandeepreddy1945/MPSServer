package com.mps.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mps.app.model.entities.TeamDetailsBoundary;

/**
 * 
 * @author Sandeep
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class TeamDetailDAOImpl implements TeamDetailDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TeamDetailsBoundary addNewTeam(TeamDetailsBoundary m) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		return m;
	}

	@Override
	public boolean deleteTeam(TeamDetailsBoundary m) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@deleteTeamByName").setParameter("teamName", m.getTeamName());
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteTeam(String teamName) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@deleteTeamByName").setParameter("teamName", teamName);
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List<TeamDetailsBoundary> listAllTeam() {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@lisAllTeams");
		session.getTransaction().commit();
		return query.getResultList();
	}

	@Override
	public void updateTeam(TeamDetailsBoundary oldM, TeamDetailsBoundary newM) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@updateTeamDetails").setParameter("teamName", newM.getTeamName())
				.setParameter("managerPortalId", newM.getManagerPortalId())
				.setParameter("projectName", newM.getProjectName());
		query.executeUpdate();
		session.getTransaction().commit();
	}

}
