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

import com.mps.app.model.entities.MemberBoundary;

/**
 * @author Sandeep
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class MemberDAOImpl implements MemberDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveMember(MemberBoundary mb) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@searchMember").setParameter("portalId", mb.getPortalId());
		if (query.getResultList().size() > 0) {
			// member already exists so donot update again.
			// the duplication calculation is done on the js side it self rather wastin a
			// service call.
			session.getTransaction().commit();
		} else {
			session.save(mb);
			session.getTransaction().commit();
		}
	}

	@Override
	public void editMember(MemberBoundary oldMb, MemberBoundary newMb) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		if (oldMb.getPortalId().equals(newMb.getPortalId())) {
			session.saveOrUpdate(newMb);
		} else {
			Query query = session.getNamedQuery("@updateMember").setParameter("portalId", newMb.getPortalId())
					.setParameter("fullName", newMb.getFullName()).setParameter("email", newMb.getEmail())
					.setParameter("designation", newMb.getDesignation())
					.setParameter("experience", newMb.getExperience()).setParameter("oldPortalId", oldMb.getPortalId());
			query.executeUpdate();
		}
		session.getTransaction().commit();
	}

	@Override
	public void deleteMember(MemberBoundary mb) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.delete(mb);
		session.getTransaction().commit();

	}

	@Override
	public List<MemberBoundary> retrieveMembers() {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.getNamedQuery("@retriveList");
		session.getTransaction().commit();
		return query.getResultList();
	}

}
