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

import org.apache.commons.io.FileUtils;

/**
 * @author Sandeep
 *
 */
public class MPSAuthServices {

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

	public static void main(String[] args)
			throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
		// MPSAuthServices.decodePublicKey();
		// MPSAuthServices.generatePrivateKey();
	}

}
