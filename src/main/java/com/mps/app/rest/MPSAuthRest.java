/**
 * 
 */
package com.mps.app.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.mps.app.model.AuthBoundary;
import com.mps.app.model.OAuthBoundary;
import com.mps.app.model.entities.Authority;
import com.mps.app.model.entities.RegisterBoundary;
import com.mps.app.model.entities.Token;
import com.mps.app.model.entities.User;
import com.mps.app.service.LoginService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
public class MPSAuthRest {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private LoginService loginService;

	@Value("${mps.oauth2.server.host}")
	private String host;

	@Value("${oauth.client.jwt.clientsecret}")
	private String client_secret;

	@Value("${oauth.client.jwt.clientid}")
	private String client_id;

	@Value("${basic.auth.clientusername}")
	private String user_name;

	@Value("${basic.auth.clientpass}")
	private String user_pass;

	@Value("${mps.oauth2.server.port}")
	private String oauthServerport;

	@Value("${mps.oauth2.server.servletcontext}")
	private String oauthUrlContext;

	@Value("${mps.oauth2.server.endpoint}")
	private String oauthEndPoint;

	@Value("${mps.oauth2.server.userendpoint}")
	private String userEndPoint;

	@Value("${mps.oauth2.defaultRoles.readId}")
	private String defaultRoleId;

	@Value("${mps.oauth2.defaultRoles.readDesc}")
	private String defaultRoleAssigned;

	@RestMethodAdvice
	@ApiOperation(value = "Consumes a login String and authenticates it.", response = ResponseEntity.class, nickname = "Login Validator")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> validateUser(String authData) throws IOException {
		AuthBoundary loginBoundary = mapper.readerFor(AuthBoundary.class).readValue(authData);
		// now make a rest call with the credentials available for authenticating the
		// user

		String oauthUrl = MPSAuthServices.buildAuthServerUrl(host, oauthServerport, oauthUrlContext) + "/"
				+ oauthEndPoint;
		String clientId = MPSAuthServices.base64ToString(client_id);
		String clientSecret = MPSAuthServices.base64ToString(client_secret);

		// email decode always to lower case.
		Map<String, String> paramMap = MPSAuthServices.buildParamMap(loginBoundary.getEmail().toLowerCase(), clientId,
				loginBoundary.getPassword());

		final Response response = RestAssured.given().auth().preemptive().basic(clientId, clientSecret).and().with()
				.params(paramMap).when().post(oauthUrl);

		// build OAuth Boundary for Token Setting .
		// OAuthBoundary authBoundary = new OAuthBoundary();
		// authBoundary.setAccess_token(response.jsonPath().getString(OAuthConstants.access_token.name()));
		// authBoundary.setRefresh_token(response.jsonPath().getString(OAuthConstants.refresh_token.name()));
		// authBoundary.setToken_type(response.jsonPath().getString(OAuthConstants.token_type.name()));
		// authBoundary.setOrganization(response.jsonPath().getString(OAuthConstants.organization.name()));
		// authBoundary.setExpires_in(response.jsonPath().getLong(OAuthConstants.expires_in.name()));
		// authBoundary.setScope(response.jsonPath().getString(OAuthConstants.scope.name()));
		// authBoundary.setJti(response.jsonPath().getString(OAuthConstants.jti.name()));

		OAuthBoundary authBoundary = mapper.readerFor(OAuthBoundary.class).readValue(response.asString());

		// set this to token boundary
		Token token = new Token();
		token.setToken(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authBoundary));

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token),
				HttpStatus.OK);

	}

	@ApiOperation(value = "Consumes a Registration String and Registers the User.", response = ResponseEntity.class, nickname = "Registration Endpoint")
	@ApiModelProperty(example = "{\"terms\":true,\"fullName\":\"Sandeep Reddy\",\"email\":\"sandeepreddy.battula@gmail.com\",\"password\":\"password\",\"confirmPassword\":\"password\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-up", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> registerUser(String registerStr) throws IOException {
		RegisterBoundary registerBoundary = mapper.readerFor(RegisterBoundary.class).readValue(registerStr);

		// validate user
		boolean isUserNotExists = loginService.registerUser(registerBoundary);
		if (isUserNotExists) {

			// first post the user to oauth server
			String userUrl = MPSAuthServices.buildAuthServerUrl(host, oauthServerport, oauthUrlContext) + "/"
					+ userEndPoint + "/add";
			String userId = MPSAuthServices.base64ToString(user_name);
			String password = MPSAuthServices.base64ToString(user_pass);

			final Response userResponse = RestAssured.given().request()
					.body(mapper.writeValueAsString(buildUserDetails(registerBoundary))).contentType(ContentType.JSON)
					.auth().preemptive().basic(userId, password).and().with().when().post(userUrl);

			System.err.println(userResponse.asString());

			/////////////////////////////////////////////////////////////////////////
			String oauthUrl = MPSAuthServices.buildAuthServerUrl(host, oauthServerport, oauthUrlContext) + "/"
					+ oauthEndPoint;
			String clientId = MPSAuthServices.base64ToString(client_id);
			String clientSecret = MPSAuthServices.base64ToString(client_secret);

			// email decode always to lower case.
			Map<String, String> paramMap = MPSAuthServices.buildParamMap(registerBoundary.getEmail().toLowerCase(),
					clientId, registerBoundary.getPassword());

			final Response response = RestAssured.given().auth().preemptive().basic(clientId, clientSecret).and().with()
					.params(paramMap).when().post(oauthUrl);

			// build OAuth Boundary for Token Setting .
			// OAuthBoundary authBoundary = new OAuthBoundary();
			// authBoundary.setAccess_token(response.jsonPath().getString(OAuthConstants.access_token.name()));
			// authBoundary.setRefresh_token(response.jsonPath().getString(OAuthConstants.refresh_token.name()));
			// authBoundary.setToken_type(response.jsonPath().getString(OAuthConstants.token_type.name()));
			// authBoundary.setOrganization(response.jsonPath().getString(OAuthConstants.organization.name()));
			// authBoundary.setExpires_in(response.jsonPath().getLong(OAuthConstants.expires_in.name()));
			// authBoundary.setScope(response.jsonPath().getString(OAuthConstants.scope.name()));
			// authBoundary.setJti(response.jsonPath().getString(OAuthConstants.jti.name()));

			OAuthBoundary authBoundary = mapper.readerFor(OAuthBoundary.class).readValue(response.asString());

			// set this to token boundary
			Token token = new Token();
			token.setToken(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authBoundary));

			return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token),
					HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	public User buildUserDetails(RegisterBoundary boundary) {
		User user = new User();
		user.setUsername(boundary.getEmail().toLowerCase());
		user.setPassword(boundary.getPassword());
		user.setAccountExpired(false);
		user.setAccountLocked(false);
		user.setCredentialsExpired(false);
		user.setEnabled(true);

		// set some default Roles
		List<Authority> authorities = new ArrayList<>();
		Authority authority = new Authority();
		authority.setId(Long.valueOf(defaultRoleId));
		authority.setName(defaultRoleAssigned);
		authorities.add(authority);

		user.setAuthorities(authorities);

		return user;
	}

}
