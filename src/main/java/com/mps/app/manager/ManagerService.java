/**
 * 
 */
package com.mps.app.manager;

import java.util.List;

/**
 * @author Sandeep
 *
 */
public interface ManagerService {

	public ManagerDetailsBoundary addNewManager(ManagerDetailsBoundary m);

	public boolean deleteManager(ManagerDetailsBoundary m);

	public boolean deleteManager(String portalId);

	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m, ManagerDetailsBoundary nm);

	public List<ManagerDetailsBoundary> listAllMangers();
}
