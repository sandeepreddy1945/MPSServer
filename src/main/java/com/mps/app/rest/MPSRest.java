/**
 * 
 */
package com.mps.app.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sandeep Reddy Battula
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class MPSRest {

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "app-api/v1/auth/sign-in", method = RequestMethod.POST)
	@ResponseBody
	public String findOne(@RequestBody String requestStr) throws JsonProcessingException {
		return mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString("Hi This Is Sandeep. This is Still in Development Process.");
	}
}
