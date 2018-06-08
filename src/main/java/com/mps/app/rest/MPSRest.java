/**
 * 
 */
package com.mps.app.rest;

import java.io.IOException;
import java.security.Key;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.app.model.LoginBoundary;
import com.mps.app.model.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * @author Sandeep Reddy Battula
 *
 */
@CrossOrigin(origins = "*")
@RestController
@Configuration
public class MPSRest {

	private static final SignatureAlgorithm HS512 = SignatureAlgorithm.HS512;
	private ObjectMapper mapper = new ObjectMapper();
	private Key macProviderKey = MacProvider.generateKey();

	@RequestMapping(value = "/app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public String findOne(@RequestBody String requestStr) throws IOException {
		LoginBoundary loginBoundary = mapper.readerFor(LoginBoundary.class).readValue(requestStr);
		String compactJws = Jwts.builder().setSubject(loginBoundary.getEmail()).signWith(HS512, macProviderKey)
				.compact();
		Token token = new Token();
		token.setToken(compactJws);
		// Jwts.parser().setSigningKey(macProviderKey).parse(compactJws).getBody();

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token);
	}
}
