package com.mps.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mps.app.model.entities.ManagerDetailsBoundary;
import com.mps.app.repository.ManagerDAO;

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
	public ManagerDetailsBoundary updateManagerDetails(ManagerDetailsBoundary m) {

		return managerDAO.updateManagerDetails(m);
	}

	@Override
	public List<ManagerDetailsBoundary> listAllMangers() {

		return managerDAO.listAllMangers();
	}

}
