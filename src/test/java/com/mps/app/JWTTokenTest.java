/**
 * 
 */
package com.mps.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;

/**
 * @author Sandeep Reddy Battula
 *
 */
public class JWTTokenTest {

	@Test
	public void testToken() {
		Map<String, Object> map = new HashMap<>();
		map.put("access_token", "1ed387aa-8931-419a-bfc5-06522656e5c7");
		map.put("token_type", "bearer");
		map.put("refresh_token", "ae9e4022-7e8f-437b-b1e8-5b4a64a056b5");
		map.put("expires_in", "5854");
		map.put("scope", "read write");
		
	}

}
