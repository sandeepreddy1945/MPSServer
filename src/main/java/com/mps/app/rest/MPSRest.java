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
import com.mps.app.model.entities.LoginBoundary;
import com.mps.app.model.entities.MemberBoundary;
import com.mps.app.model.entities.RegisterBoundary;
import com.mps.app.model.entities.Token;
import com.mps.app.service.LoginService;
import com.mps.app.service.MemberService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sandeep Reddy Battula
 *
 */
@CrossOrigin(origins = "*")
@RestController
@Api
public class MPSRest {

	private static final SignatureAlgorithm HS512 = SignatureAlgorithm.HS512;
	private ObjectMapper mapper = new ObjectMapper();
	private Key macProviderKey = MacProvider.generateKey();

	@Autowired
	private LoginService loginService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "Consumes a login String and authenticates it.", response = ResponseEntity.class, nickname = "Login Validator")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> authenticatUser(@RequestBody String loginStr) throws IOException {
		LoginBoundary loginBoundary = mapper.readerFor(LoginBoundary.class).readValue(loginStr);
		// validate user
		boolean isUserExists = loginService.validateUser(loginBoundary);

		if (isUserExists) {

			String compactJws = Jwts.builder().setSubject(loginBoundary.getEmail()).signWith(HS512, macProviderKey)
					.compact();
			Token token = new Token();
			token.setToken(compactJws);
			// Jwts.parser().setSigningKey(macProviderKey).parse(compactJws).getBody();

			return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token),
					HttpStatus.ACCEPTED);

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Consumes a Registration String and Registers the User.", response = ResponseEntity.class, nickname = "Registration Endpoint")
	@ApiModelProperty(example = "{\"terms\":true,\"fullName\":\"Sandeep Reddy\",\"email\":\"sandeepreddy.battula@gmail.com\",\"password\":\"password\",\"confirmPassword\":\"password\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-up", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> registerUser(@RequestBody String registerStr) throws IOException {
		RegisterBoundary registerBoundary = mapper.readerFor(RegisterBoundary.class).readValue(registerStr);

		// validate user
		boolean isUserExists = loginService.registerUser(registerBoundary);
		if (isUserExists) {

			String compactJws = Jwts.builder().setSubject(registerBoundary.getEmail()).signWith(HS512, macProviderKey)
					.compact();
			Token token = new Token();
			token.setToken(compactJws);

			return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token),
					HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Consumes a Email String and Resends the User a new password.", response = ResponseEntity.class, nickname = "Forgot Password Endpoint")
	@ApiModelProperty(example = "{\"email\":\"sandeepreddy.battula@gmail.com\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/request-pass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> forgotPassword(@RequestBody String forgotPassStr) throws IOException {
		LoginBoundary loginBoundary = mapper.readerFor(LoginBoundary.class).readValue(forgotPassStr);

		boolean isUserExists = loginService.checkIfUserExists(loginBoundary.getEmail());
		if (isUserExists) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Consumes a Reset String and Resets the User password.", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "{\"email\":\"sandeepreddy.battula@gmail.com\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/reset-pass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestBody String resetPass) throws IOException {
		// for now needs more understanding on this
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Consumes save member string and saves the member", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/member/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveMember(@RequestBody String saveMember) throws IOException {
		MemberBoundary member = mapper.readerFor(MemberBoundary.class).readValue(saveMember);

		memberService.saveMember(member);
		return new ResponseEntity<>(mapper.writeValueAsString(member), HttpStatus.OK);
	}
}
