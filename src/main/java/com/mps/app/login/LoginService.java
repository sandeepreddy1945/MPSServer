/**
 * 
 */
package com.mps.app.login;

/**
 * @author Sandeep
 *
 */
public interface LoginService {

	public boolean validateUser(LoginBoundary loginBoundary);
	
	public void addUser(LoginBoundary loginBoundary);
	
	public boolean registerUser(RegisterBoundary registerBoundary);
	
	public boolean checkIfUserExists(String loginEmail);
	
	public void updatePassword(String emailId, String password);
	
	public void updateUserImage(String emailId, String imgData);
	
	public LoginBoundary fecthUserImage(String emailId);
}
