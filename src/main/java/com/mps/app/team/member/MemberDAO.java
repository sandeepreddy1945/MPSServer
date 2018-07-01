/**
 * 
 */
package com.mps.app.team.member;

import java.util.List;

/**
 * @author Sandeep
 *
 */
public interface MemberDAO {

	public void saveMember(MemberBoundary mb);

	public void editMember(MemberBoundary oldMb, MemberBoundary newMb);

	public void deleteMember(MemberBoundary mb);
	
	public void deleteMember(String portalId);

	public List<MemberBoundary> retrieveMembers();
	
	public void updateMember(MemberBoundary mb);

}
