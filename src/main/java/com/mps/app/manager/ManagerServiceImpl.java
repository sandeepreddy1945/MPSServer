package com.mps.app.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Sandeep
 *
 */
@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerDAO managerDAO;

	@Override
	public ManagerDetailsBoundary addNewManager(ManagerDetailsBoundary m) {

		return managerDAO.addNewManager(m);
	}

	@Override
	public boolean deleteManager(ManagerDetailsBoundary m) {

		return managerDAO.deleteManager(m);
	}

	@Override
	public boolean deleteManager(String portalId) {

		return managerDAO.deleteManager(portalId);
	}

	@Override
	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m, ManagerDetailsBoundary nm) {

		return managerDAO.updateManagerDetails(m, nm);
	}

	@Override
	public List<ManagerDetailsBoundary> listAllMangers() {

		return managerDAO.listAllMangers();
	}

}
