/**
 * 
 */
package com.mps.app.team.member;

import java.io.IOException;
import java.security.Key;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.app.login.LoginBoundary;
import com.mps.app.login.LoginService;
import com.mps.app.rest.MPSAuthServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

	private static final SignatureAlgorithm ALGORITHM_RS256 = SignatureAlgorithm.RS256;
	private ObjectMapper mapper = new ObjectMapper();
	private Key RSA_PRIVATE_KEY = MPSAuthServices.getKey("PRIVATE");

	@Autowired
	private LoginService loginService;

	@Autowired
	private MemberService memberService;

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
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Consumes a Reset String and Resets the User password.", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "{\"email\":\"sandeepreddy.battula@gmail.com\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/auth/reset-pass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestBody String resetPass) throws IOException, JSONException {
		JSONObject resetPassJson = new JSONObject(resetPass);
		// retrieves the token and other stuff here.
		String token = resetPassJson.getString("token");
		// unparse the token submitted
		JSONObject sub = new JSONObject(Jwts.parser().setSigningKey(RSA_PRIVATE_KEY).parse(token).getBody().toString());
		String emailId = sub.getString("sub");
		String password = resetPassJson.getString("password");
		boolean isUserExists = loginService.checkIfUserExists(emailId);
		if (isUserExists) {
			loginService.updatePassword(emailId, password);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			// for now needs more understanding on this
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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

	@ApiOperation(value = "Consumes Edit Member Set string and saves the member", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/member/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEditMember(@RequestBody String saveMember) throws IOException, JSONException {
		JSONArray array = new JSONArray(saveMember);
		MemberBoundary oldMbr = mapper.readerFor(MemberBoundary.class).readValue(array.getString(0));
		MemberBoundary newMbr = mapper.readerFor(MemberBoundary.class).readValue(array.getString(1));

		memberService.editMember(oldMbr, newMbr);
		return new ResponseEntity<>(mapper.writeValueAsString(oldMbr), HttpStatus.OK);
	}

	@ApiOperation(value = "Fetches all the Members and Posts it.", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/member/reteriveAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> reteriveAllMembers() throws IOException, JSONException {
		List<MemberBoundary> memberBoundaries = memberService.retrieveMembers();
		return new ResponseEntity<>(mapper.writeValueAsString(memberBoundaries), HttpStatus.OK);
	}

	@ApiOperation(value = "Delte's a member based on the Portal Id", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/member/deleteMember/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteMemberByPotalId(@PathVariable("id") String portalId)
			throws IOException, JSONException {
		if (portalId != null) {
			memberService.deleteMember(portalId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@ApiOperation(value = "Delte's a member based on the Member Object received and uses from it Portal Id", response = ResponseEntity.class, nickname = "Reset Password Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/member/deleteMember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteMember(@RequestBody String deleteMember) throws IOException, JSONException {
		MemberBoundary member = mapper.readerFor(MemberBoundary.class).readValue(deleteMember);
		memberService.deleteMember(member);
		return new ResponseEntity<>(mapper.writeValueAsString(member), HttpStatus.OK);
	}

	@ApiOperation(value = "Updates the Profile Pic of The User.", response = ResponseEntity.class, nickname = "Update Profile Pic Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/image/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> saveProfilePic(@RequestBody String updateImage) throws IOException, JSONException {
		JSONObject resetPassJson = new JSONObject(updateImage);
		// retrieves the token and other stuff here.
		String token = resetPassJson.getString("token");
		// unparse the token submitted
		JSONObject sub = new JSONObject(Jwts.parser().setSigningKey(RSA_PRIVATE_KEY).parse(token).getBody().toString());
		String emailId = sub.getString("sub");
		String imgData = resetPassJson.getString("imageData");
		boolean isUserExists = loginService.checkIfUserExists(emailId);
		if (isUserExists) {
			loginService.updateUserImage(emailId, imgData);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			// for now needs more understanding on this
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Fetch the Profile Pic of The User.", response = ResponseEntity.class, nickname = "Fetch Profile Pic Endpoint")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/image/fetch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> fetchProfilePic(@RequestBody String fetchProfilePic)
			throws IOException, JSONException {
		JSONObject resetPassJson = new JSONObject(fetchProfilePic);
		// retrieves the token and other stuff here.
		String token = resetPassJson.getString("token");
		// unparse the token submitted
		JSONObject sub = new JSONObject(Jwts.parser().setSigningKey(RSA_PRIVATE_KEY).parse(token).getBody().toString());
		String emailId = sub.getString("sub");
		LoginBoundary user = loginService.fecthUserImage(emailId);
		if (user.getImageData() != null && user.getImageData().length() > 0) {

			return new ResponseEntity<>(mapper.writeValueAsString(user), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(mapper.writeValueAsString(user), HttpStatus.NOT_FOUND);
		}
	}
}
