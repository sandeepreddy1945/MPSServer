package com.mps.app.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author Sandeep Reddy Battula
 *
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Allow console and swagger ui html files for display.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/console/**").and().ignoring().antMatchers("/swagger-ui.html/**");
	}
}
