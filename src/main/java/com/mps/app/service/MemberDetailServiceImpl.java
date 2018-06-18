package com.mps.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mps.app.model.entities.MemberBoundary;
import com.mps.app.repository.MemberDetailDAO;

/**
 * 
 * @author Sandeep
 *
 */
@Service
public class MemberDetailServiceImpl implements MemberDetailService {

	@Autowired
	private MemberDetailDAO mDao;

	@Override
	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m) {

		return mDao.saveUpdateMemberDetails(m);
	}

	@Override
	public MemberBoundary fecthMemberDetail(String portalId) {

		return mDao.fecthMemberDetail(portalId);
	}

}
