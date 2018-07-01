/**
 * 
 */
package com.mps.app.team.member;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

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
public class MPSMemberDetailRest {

	@Autowired
	private MemberDetailService mds;

	private ObjectMapper mapper = new ObjectMapper();

	@ApiOperation(value = "Save's Member Details", response = ResponseEntity.class, nickname = "Save Member Detail")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/memberdetail/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<MemberBoundary> saveMemberDetail(@RequestBody MemberBoundary m)
			throws IOException, JSONException {
		m = mds.saveUpdateMemberDetails(m);

		return new ResponseEntity<MemberBoundary>(m, HttpStatus.ACCEPTED);

	}

	@ApiOperation(value = "Fetch Member Details", response = ResponseEntity.class, nickname = "Fetch Member Details")
	@ApiModelProperty(example = "", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/api/memberdetail/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> fetchMemberDetail(@RequestParam("portalId") String portalId)
			throws IOException, JSONException {

		MemberBoundary m = mds.fecthMemberDetail(portalId);

		return new ResponseEntity<String>(mapper.writeValueAsString(m), HttpStatus.ACCEPTED);

	}
}
