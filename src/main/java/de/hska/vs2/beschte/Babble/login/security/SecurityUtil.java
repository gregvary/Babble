package de.hska.vs2.beschte.Babble.login.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class SecurityUtil {

	public static String getUserPasswordHashed(String password, String username) {
		password = password + username;
	    MessageDigest sha256;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			byte[] passBytes = password.getBytes();
			byte[] passHash = sha256.digest(passBytes);
			return Base64.getEncoder().encodeToString(passHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
