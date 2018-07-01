package com.mps.app.team.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
