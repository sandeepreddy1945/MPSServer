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

import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.RegisterBoundary;

/**
 * @author Sandeep
 *
 */
@Repository
@SuppressWarnings("unchecked")
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
		session.getTransaction().commit();
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
			boundary.setEmail(registerBoundary.getEmail().toLowerCase());
			boundary.setPassword(registerBoundary.getPassword());
			boundary.setRegisterBoundary(registerBoundary);
			session.save(boundary);
			session.getTransaction().commit();
			return true;
		}
	}

	@Override
	public boolean checkIfUserExists(String loginEmail) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@CheckUserExistance").setParameter("email", loginEmail.toLowerCase());

		List<LoginBoundary> loginBoundaries = query.getResultList();
		session.getTransaction().commit();
		return loginBoundaries == null ? Boolean.FALSE : loginBoundaries.size() > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void updatePassword(String emailId, String password) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@resetPassword").setParameter("pass", password).setParameter("email",
				emailId);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public void updateUserImage(String emailId, String imgData) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@updateUserImage").setParameter("imgData", imgData).setParameter("email",
				emailId);
		query.executeUpdate();
		session.getTransaction().commit();

	}

	@Override
	public LoginBoundary fecthUserImage(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@CheckUserExistance").setParameter("email", emailId.toLowerCase());

		List<LoginBoundary> loginBoundaries = query.getResultList();
		session.getTransaction().commit();

		return loginBoundaries.get(0);
	}

}
