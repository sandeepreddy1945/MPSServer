/**
 * 
 */
package com.mps.app.rest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * @author Sandeep
 *
 */
public class MPSAuthServices {

	public static BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

	public static PublicKey getPublicKey()
			throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
		File pubicKeyTmpFile = File.createTempFile("publickey", "key");
		FileUtils.copyInputStreamToFile(MPSAuthServices.class.getClassLoader().getResourceAsStream("publickey.key"),
				pubicKeyTmpFile);
		Path path = Paths.get(pubicKeyTmpFile.toURI());

		byte[] bytes2 = Files.readAllBytes(path);
		/* Generate public key. */
		X509EncodedKeySpec ks2 = new X509EncodedKeySpec(bytes2);
		KeyFactory kf2 = KeyFactory.getInstance("RSA");
		PublicKey pub = kf2.generatePublic(ks2);

		return pub;
	}

	public static PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		/* Read all bytes from the private key file */
		File pubicKeyTmpFile = File.createTempFile("privatekey", "key");
		FileUtils.copyInputStreamToFile(MPSAuthServices.class.getClassLoader().getResourceAsStream("privatekey.key"),
				pubicKeyTmpFile);
		Path path = Paths.get(pubicKeyTmpFile.toURI());
		byte[] bytes = Files.readAllBytes(path);

		/* Generate private key. */
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);

		return pvt;
	}

	public static Key getKey(String name) {
		if (name.equalsIgnoreCase("PRIVATE")) {
			try {
				return getPrivateKey();
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				return getPublicKey();
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | URISyntaxException | IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static Map<String, String> buildParamMap(final String userName, final String clientId,
			final String password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("grant_type", "password");
		paramMap.put("username", userName);
		paramMap.put("password", password);
		paramMap.put("client_id", clientId);

		return paramMap;
	}

	public static String base64ToString(String base64Str) {
		return new String(Base64Utils.decodeFromString(base64Str));
	}

	public static String stringToBase64(String str) {
		return Base64Utils.encodeToString(str.getBytes());
	}

	public static String buildAuthServerUrl(String host, String port, String serverContext) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(serverContext);
		return sb.toString();
	}
}
