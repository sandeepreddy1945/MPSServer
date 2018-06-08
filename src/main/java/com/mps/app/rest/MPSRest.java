/**
 * 
 */
package com.mps.app.rest;

import java.security.Key;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public String findOne(@RequestBody String requestStr) throws JsonProcessingException {
		Key key = MacProvider.generateKey();
		String compactJws = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, key).compact();
		Token token = new Token();
		token.setToken(compactJws);
		
		Jwts.parser().setSigningKey(key).parse(compactJws).getBody();

		return mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(token);
	}
}
