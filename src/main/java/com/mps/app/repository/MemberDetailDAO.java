/**
 * 
 */
package com.mps.app.repository;

import com.mps.app.model.entities.MemberBoundary;

/**
 * @author Sandeep
 *
 */
public interface MemberDetailDAO {

	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m);

	public MemberBoundary fecthMemberDetail(String portalId);
}
