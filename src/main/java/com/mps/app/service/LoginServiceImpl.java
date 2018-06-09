/**
 * 
 */
package com.mps.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.RegisterBoundary;
import com.mps.app.repository.LoginDAO;

/**
 * @author Sandeep
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO loginDAO;

	@Override
	public boolean validateUser(LoginBoundary loginBoundary) {

		return loginDAO.validateUser(loginBoundary);
	}

	@Override
	public void addUser(LoginBoundary loginBoundary) {
		loginDAO.addUser(loginBoundary);
	}

	@Override
	public boolean registerUser(RegisterBoundary registerBoundary) {
		return loginDAO.registerUser(registerBoundary);

	}

	@Override
	public boolean checkIfUserExists(String loginEmail) {
		return loginDAO.checkIfUserExists(loginEmail);
	}

}
