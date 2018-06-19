package com.mps.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mps.app.model.entities.MemberBoundary;

/**
 * 
 * @author Sandeep
 *
 */
@Repository
public class MemberDetailDAOImpl implements MemberDetailDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m) {
		Session session = entityManager.unwrap(Session.class);
		MemberBoundary mb = this.fecthMemberDetail(m.getPortalId());
		// if member not found save it or else merge.
		if (mb == null) {
			session.beginTransaction();
			session.save(m);
			session.getTransaction().commit();
		} else {
			m.setMemberId(mb.getMemberId());
			session.beginTransaction();
			session.merge(m);
			session.getTransaction().commit();
		}
		return m;
	}

	@Override
	public MemberBoundary fecthMemberDetail(String portalId) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.createNamedQuery("@searchMember").setParameter("portalId", portalId);
		session.getTransaction().commit();
		if (query.getResultList() != null && query.getResultList().size() > 0)
			return (MemberBoundary) query.getResultList().get(0);
		else
			return null;
	}

}
