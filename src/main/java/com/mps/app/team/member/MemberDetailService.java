/**
 * 
 */
package com.mps.app.team.member;

/**
 * @author Sandeep
 *
 */
public interface MemberDetailService {

	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m);

	public MemberBoundary fecthMemberDetail(String portalId);
}
