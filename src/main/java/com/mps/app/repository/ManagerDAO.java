/**
 * 
 */
package com.mps.app.repository;

import java.util.List;

import com.mps.app.model.entities.ManagerDetailsBoundary;

/**
 * @author Sandeep
 *
 */
public interface ManagerDAO {
	
	public ManagerDetailsBoundary addNewManager(ManagerDetailsBoundary m);

	public boolean deleteManager(ManagerDetailsBoundary m);

	public boolean deleteManager(String portalId);

	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m, ManagerDetailsBoundary nm);

	public List<ManagerDetailsBoundary> listAllMangers();
}
