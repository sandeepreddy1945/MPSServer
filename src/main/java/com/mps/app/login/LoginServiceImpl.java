/**
 * 
 */
package com.mps.app.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void updatePassword(String emailId, String password) {
		loginDAO.updatePassword(emailId, password);
	}

	@Override
	public void updateUserImage(String emailId, String imgData) {
		loginDAO.updateUserImage(emailId, imgData);	
	}

	@Override
	public LoginBoundary fecthUserImage(String emailId) {
		return loginDAO.fecthUserImage(emailId);
	}

}
