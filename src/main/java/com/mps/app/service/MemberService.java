/**
 * 
 */
package com.mps.app.service;

import java.util.List;

import com.mps.app.model.entities.MemberBoundary;

/**
 * @author Sandeep
 *
 */
public interface MemberService {

	public void saveMember(MemberBoundary mb);

	public void editMember(MemberBoundary oldMb, MemberBoundary newMb);

	public void deleteMember(MemberBoundary mb);
	
	public void deleteMember(String portalId);

	public List<MemberBoundary> retrieveMembers();
}
