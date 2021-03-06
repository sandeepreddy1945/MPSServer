/**
 * 
 */
package com.mps.app.teamdetails;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.app.annotations.RestMethodAdvice;

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
public class MPSTeamRest {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private TeamDetailService tds;

	@ApiOperation(value = "Consumes a String and adds a New Team", response = ResponseEntity.class, nickname = "Add Team")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/team/addTeam", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TeamDetailsBoundary> addNewTeam(@RequestBody TeamDetailsBoundary tdb) throws IOException {
		tdb = tds.addNewTeam(tdb);
		return new ResponseEntity<TeamDetailsBoundary>(tdb, HttpStatus.ACCEPTED);

	}

	@ApiOperation(value = "Consumes a String deletes a Team", response = ResponseEntity.class, nickname = "Delete Team")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/team/delete/deleteTeam", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TeamDetailsBoundary> deleteTeamByBoundary(@RequestBody TeamDetailsBoundary tdb)
			throws IOException {
		tds.deleteTeam(tdb);
		return new ResponseEntity<TeamDetailsBoundary>(tdb, HttpStatus.OK);

	}

	@ApiOperation(value = "Consumes a String deletes a Team", response = ResponseEntity.class, nickname = "Delete Team")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/team/deleteTeam", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteTeamByName(@RequestParam("teamName") String teamName) throws IOException {

		tds.deleteTeam(teamName);
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@RestMethodAdvice
	@ApiOperation(value = "Consumes a String deletes a Team", response = ResponseEntity.class, nickname = "Delete Team")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/team/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<TeamDetailsBoundary>> listAllTeams() throws IOException {

		List<TeamDetailsBoundary> l = tds.listAllTeam();

		return new ResponseEntity<List<TeamDetailsBoundary>>(l, HttpStatus.OK);

	}

	@ApiOperation(value = "Consumes a String Updates a Team", response = ResponseEntity.class, nickname = "Updates Team")
	@ApiModelProperty(example = "{\"email:\"sandeepreddy@gmail.com\",\"password\":\"pass\"}", required = true, allowEmptyValue = false)
	@RequestMapping(value = "/app-api/v1/team/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TeamDetailsBoundary> editMemberDetails(@RequestBody String updateTeamStr)
			throws IOException, JSONException {
		JSONArray array = new JSONArray(updateTeamStr);
		TeamDetailsBoundary oldMbr = mapper.readerFor(TeamDetailsBoundary.class).readValue(array.getString(0));
		TeamDetailsBoundary newMbr = mapper.readerFor(TeamDetailsBoundary.class).readValue(array.getString(1));

		tds.updateTeam(oldMbr, newMbr);

		return new ResponseEntity<TeamDetailsBoundary>(newMbr, HttpStatus.OK);

	}
}
