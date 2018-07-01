package com.mps.app.team.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Sandeep
 *
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public void saveMember(MemberBoundary mb) {
		memberDAO.saveMember(mb);

	}

	@Override
	public void editMember(MemberBoundary oldMb, MemberBoundary newMb) {
		memberDAO.editMember(oldMb, newMb);

	}

	@Override
	public void deleteMember(MemberBoundary mb) {
		memberDAO.deleteMember(mb);

	}

	@Override
	public List<MemberBoundary> retrieveMembers() {

		return memberDAO.retrieveMembers();
	}

	@Override
	public void deleteMember(String portalId) {
		memberDAO.deleteMember(portalId);
	}

	@Override
	public void updateMember(MemberBoundary mb) {
		
		memberDAO.updateMember(mb);
	}

}
