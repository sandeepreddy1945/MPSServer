/**
 * 
 */
package com.mps.app.manager;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author Sandeep
 *
 */
@CrossOrigin(origins = "*")
@RestController
@Api
public class MPSManagerRest {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ManagerService managerService;

	@ApiOperation(value = "Consumes a String and adds a New Manager", response = ResponseEntity.class, nickname = "Add Manager")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/manager/addManager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ManagerDetailsBoundary> addNewManager(@RequestBody ManagerDetailsBoundary mb)
			throws IOException {

		ManagerDetailsBoundary mbs = managerService.addNewManager(mb);
		return new ResponseEntity<ManagerDetailsBoundary>(mbs, HttpStatus.ACCEPTED);

	}

	@ApiOperation(value = "Deletes A Manager Consuming his Id", response = ResponseEntity.class, nickname = "Delete Manager")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/manager/delete/manager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ManagerDetailsBoundary> deleteManagerByBoundary(@RequestBody ManagerDetailsBoundary mb)
			throws IOException {
		boolean mbs = managerService.deleteManager(mb);
		return new ResponseEntity<ManagerDetailsBoundary>(mb, HttpStatus.OK);

	}

	@ApiOperation(value = "Deletes A Manager Consuming his Id", response = ResponseEntity.class, nickname = "Delete Manager")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/manager/delete/manager", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteManagerById(@Param("portalId") String portalId) throws IOException {
		boolean mbs = managerService.deleteManager(portalId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	// @PreAuthorize("#oauth2.hasScope('write') and #oauth2.hasScope('read') and hasAuthority('COMPANY_READ')")
	@ApiOperation(value = "List All Available Managers", response = ResponseEntity.class, nickname = "List Managers")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/manager/list", method = RequestMethod.GET)
	@ResponseBody
	@Authorization(value = "basic")
	public ResponseEntity<List<ManagerDetailsBoundary>> retrieveAllManagers() throws IOException {
		List<ManagerDetailsBoundary> lmd = managerService.listAllMangers();

		return new ResponseEntity<List<ManagerDetailsBoundary>>(lmd, HttpStatus.OK);

	}

	@ApiOperation(value = "Consumes a String Updates a Manager", response = ResponseEntity.class, nickname = "Updates Manager")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/manager/editManager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editManagerDetails(@RequestBody String updateTeamStr)
			throws IOException, JSONException {
		JSONArray array = new JSONArray(updateTeamStr);
		ManagerDetailsBoundary oldMbr = mapper.readerFor(ManagerDetailsBoundary.class).readValue(array.getString(0));
		ManagerDetailsBoundary newMbr = mapper.readerFor(ManagerDetailsBoundary.class).readValue(array.getString(1));

		managerService.updateManagerDetails(oldMbr, newMbr);

		return new ResponseEntity<String>(mapper.writeValueAsString(newMbr), HttpStatus.OK);

	}
}
