/**
 * 
 */
package com.mps.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.junit.Test;

/**
 * @author Sandeep
 *
 */
public class AuthTest {

	@Test
	public void authTest() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();

		File file = new File("publickey.key");
		File file2 = new File("privatekey.key");

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(kp.getPublic().getEncoded());
		fileOutputStream.flush();

		fileOutputStream.close();

		fileOutputStream = new FileOutputStream(file2);
		fileOutputStream.write(kp.getPrivate().getEncoded());
		fileOutputStream.flush();

		System.err.println("Private key format: " + kp.getPrivate().getFormat());
		// prints "Private key format: PKCS#8" on my machine

		System.err.println("Public key format: " + kp.getPublic().getFormat());
		// prints "Public key format: X.509" on my machine
		fileOutputStream.close();

		/* Read all bytes from the private key file */
		Path path = Paths.get(file2.toURI());
		byte[] bytes = Files.readAllBytes(path);

		/* Generate private key. */
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);

		/* Read all the public key bytes */
		Path path2 = Paths.get(file.toURI());
		byte[] bytes2 = Files.readAllBytes(path2);

		/* Generate public key. */
		X509EncodedKeySpec ks2 = new X509EncodedKeySpec(bytes2);
		KeyFactory kf2 = KeyFactory.getInstance("RSA");
		PublicKey pub = kf2.generatePublic(ks2);
	}
}
