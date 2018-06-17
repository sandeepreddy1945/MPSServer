/**
 * 
 */
package com.mps.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mps.app.model.entities.ManagerDetailsBoundary;

/**
 * @author Sandeep
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class ManagerDAOImpl implements ManagerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ManagerDetailsBoundary addNewManager(ManagerDetailsBoundary m) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		return m;
	}

	@Override
	public boolean deleteManager(ManagerDetailsBoundary m) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@deleteManagerByPortal").setParameter("portalId", m.getPortalId());
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteManager(String portalId) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@deleteManagerByPortal").setParameter("portalId", portalId);
		query.executeUpdate();
		session.getTransaction().commit();
		return false;
	}

	@Override
	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m, ManagerDetailsBoundary nm) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@updateManagerDetail").setParameter("portalId", nm.getPortalId())
				.setParameter("designation", nm.getDesignation()).setParameter("name", nm.getName())
				.setParameter("email", nm.getEmail()).setParameter("experience", nm.getExperience());
		query.executeUpdate();
		session.getTransaction().commit();
		return nm;
	}

	@Override
	public List<ManagerDetailsBoundary> listAllMangers() {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@listAllManagers");
		session.getTransaction().commit();
		return query.getResultList();
	}

}
