/**
 * 
 */
package com.mps.app.service;

import com.mps.app.model.entities.MemberBoundary;

/**
 * @author Sandeep
 *
 */
public interface MemberDetailService {

	public MemberBoundary saveUpdateMemberDetails(MemberBoundary m);

	public MemberBoundary fecthMemberDetail(String portalId);
}
