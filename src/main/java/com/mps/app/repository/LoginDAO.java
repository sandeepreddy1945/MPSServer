/**
 * 
 */
package com.mps.app.repository;

import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.RegisterBoundary;

/**
 * @author Sandeep
 *
 */
public interface LoginDAO {

	public boolean validateUser(LoginBoundary loginBoundary);
	
	public void addUser(LoginBoundary loginBoundary);
	
	public boolean registerUser(RegisterBoundary registerBoundary);
	
	public boolean checkIfUserExists(String loginEmail);
	
	public void updatePassword(String emailId, String password);
	
	public void updateUserImage(String emailId, String imgData);
	
	public LoginBoundary fecthUserImage(String emailId);
}
