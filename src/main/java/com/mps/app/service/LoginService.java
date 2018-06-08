/**
 * 
 */
package com.mps.app.service;

import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.RegisterBoundary;

/**
 * @author Sandeep
 *
 */
public interface LoginService {

	public boolean validateUser(LoginBoundary loginBoundary);
	
	public void addUser(LoginBoundary loginBoundary);
	
	public boolean registerUser(RegisterBoundary registerBoundary);
}
