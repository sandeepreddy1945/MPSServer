/**
 * 
 */
package com.mps.app.repository;

import static org.assertj.core.api.Assertions.useRepresentation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.RegisterBoundary;

/**
 * @author Sandeep
 *
 */
@Repository
public class LoginDAOImpl implements LoginDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean validateUser(LoginBoundary lb) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@ValidateLogin").setParameter("email", lb.getEmail().toLowerCase())
				.setParameter("pass", lb.getPassword());

		List<LoginBoundary> loginBoundaries = query.getResultList();
		return loginBoundaries == null ? Boolean.FALSE : loginBoundaries.size() > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addUser(LoginBoundary loginBoundary) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.saveOrUpdate(loginBoundary);
		session.getTransaction().commit();
	}

	@Override
	public boolean registerUser(RegisterBoundary registerBoundary) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@CheckForUser").setParameter("email",
				registerBoundary.getEmail().toLowerCase());
		List<RegisterBoundary> rbs = query.getResultList();
		if (rbs != null && rbs.size() > 0) {
			// user already exists so return false
			return false;
		} else {
			session.save(registerBoundary);
			LoginBoundary boundary = new LoginBoundary();
			boundary.setEmail(registerBoundary.getEmail());
			boundary.setPassword(registerBoundary.getPassword());
			boundary.setRegisterBoundary(registerBoundary);
			session.save(boundary);
			session.getTransaction().commit();
			return true;
		}
	}

}
