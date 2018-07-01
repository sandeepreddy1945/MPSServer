/**
 * 
 */
package com.mps.app.team.member;

/**
 * @author Sandeep
 *
 */
public interface MemberDetailDAO {

	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m);

	public MemberBoundary fecthMemberDetail(String portalId);
}
