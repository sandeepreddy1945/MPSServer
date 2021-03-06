/**
 * 
 */
package com.mps.app.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
import com.mps.app.rest.MPSAuthServices;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandeep Reddy Battula
 *
 */
@CrossOrigin(origins = "*")
@RestController
@Api
@Slf4j
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
	@Cacheable
	public ResponseEntity<Token> validateUser(@RequestBody AuthBoundary loginBoundary) throws IOException {

		String oauthUrl = MPSAuthServices.buildAuthServerUrl(host, oauthServerport, oauthUrlContext) + "/"
				+ oauthEndPoint;
		String clientId = MPSAuthServices.base64ToString(client_id);
		String clientSecret = MPSAuthServices.base64ToString(client_secret);

		// email decode always to lower case.
		Map<String, String> paramMap = MPSAuthServices.buildParamMap(loginBoundary.getEmail().toLowerCase(), clientId,
				loginBoundary.getPassword());

		final Response response = getResponseFromOAuthServer(oauthUrl, clientId, clientSecret, paramMap);

		OAuthBoundary authBoundary = mapper.readerFor(OAuthBoundary.class).readValue(response.asString());
		log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authBoundary));

		if (authBoundary.getError() != null) {
			return new ResponseEntity<Token>(new Token(authBoundary.getError_description()), HttpStatus.UNAUTHORIZED);
		}

		// set this to token boundary
		Token token = new Token();
		token.setToken(authBoundary.getAccess_token());

		return new ResponseEntity<Token>(token, HttpStatus.OK);

	}

	@ApiOperation(value = "Consumes a Registration String and Registers the User.", response = ResponseEntity.class, nickname = "Registration Endpoint")
	@ApiModelProperty(example = "{\"terms\":true,\"fullName\":\"Sandeep Reddy\",\"email\":\"sandeepreddy.battula@gmail.com\",\"password\":\"password\",\"confirmPassword\":\"password\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/sign-up", method = RequestMethod.POST)
	@ResponseBody
	@Cacheable
	public ResponseEntity<Token> registerUser(@RequestBody RegisterBoundary registerBoundary) throws IOException {

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

			final Response response = getResponseFromOAuthServer(oauthUrl, clientId, clientSecret, paramMap);

			OAuthBoundary authBoundary = mapper.readerFor(OAuthBoundary.class).readValue(response.asString());
			log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authBoundary));

			if (authBoundary.getError() != null) {
				return new ResponseEntity<Token>(new Token(authBoundary.getError_description()),
						HttpStatus.UNAUTHORIZED);
			}

			// set this to token boundary
			Token token = new Token();
			token.setToken(authBoundary.getAccess_token());

			return new ResponseEntity<Token>(token, HttpStatus.CREATED);

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

	private Response getResponseFromOAuthServer(String oauthUrl, String clientId, String clientSecret,
			Map<String, String> paramMap) {
		return RestAssured.given().auth().preemptive().basic(clientId, clientSecret).and().with().params(paramMap)
				.when().post(oauthUrl);
	}

}
