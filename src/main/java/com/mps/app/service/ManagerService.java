/**
 * 
 */
package com.mps.app.service;

import java.util.List;

import com.mps.app.model.entities.ManagerDetailsBoundary;

/**
 * @author Sandeep
 *
 */
public interface ManagerService {

	public ManagerDetailsBoundary addNewManager(ManagerDetailsBoundary m);

	public boolean deleteManager(ManagerDetailsBoundary m);

	public boolean deleteManager(String portalId);

	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m);

	public List<ManagerDetailsBoundary> listAllMangers();
}
