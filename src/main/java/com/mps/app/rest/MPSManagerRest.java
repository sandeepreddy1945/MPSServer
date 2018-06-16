/**
 * 
 */
package com.mps.app.rest;

import java.io.IOException;
import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.app.annotations.RestMethodAdvice;
import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.Token;
import com.mps.app.service.LoginService;
import com.mps.app.service.MemberService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sandeep
 *
 */
@CrossOrigin(origins = "*")
@RestController
@Api
public class MPSManagerRest {

	private static final SignatureAlgorithm ALGORITHM_RS256 = SignatureAlgorithm.RS256;
	private ObjectMapper mapper = new ObjectMapper();
	private Key RSA_PRIVATE_KEY = MPSAuthServices.getKey("PRIVATE");

	@Autowired
	private LoginService loginService;

	@Autowired
	private MemberService memberService;

	@RestMethodAdvice
	@ApiOperation(value = "Consumes a login String and authenticates it.", response = ResponseEntity.class, nickname = "Login Validator")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> authenticatUser(@RequestBody String loginStr) throws IOException {
		LoginBoundary loginBoundary = mapper.readerFor(LoginBoundary.class).readValue(loginStr);
		// validate user
		boolean isUserExists = loginService.validateUser(loginBoundary);

		if (isUserExists) {

			String compactJws = Jwts.builder().setSubject(loginBoundary.getEmail().toLowerCase())
					.signWith(ALGORITHM_RS256, RSA_PRIVATE_KEY).compact();
			Token token = new Token();
			token.setToken(compactJws);

			return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token),
					HttpStatus.ACCEPTED);

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
