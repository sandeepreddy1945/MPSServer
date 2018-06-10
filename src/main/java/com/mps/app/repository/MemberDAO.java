/**
 * 
 */
package com.mps.app.repository;

import java.util.List;

import com.mps.app.model.entities.MemberBoundary;

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

}
