package com.backend.base.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class SecurityUtil {

	public static String newToken() throws NoSuchAlgorithmException {

		String uuid = UUID.randomUUID().toString();

		return uuid;
	}

	public static String newTokenSHA256() throws NoSuchAlgorithmException {

		String uuid = UUID.randomUUID().toString();

		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] result = sha.digest(uuid.getBytes());

		return hexEncode(result);
	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.encryptPassword(password);
	}

	public static boolean checkPassword(String inputPassword, String encryptedPassword) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
			return true;
		}
		return false;
	}

	/**
	 * The byte[] returned by MessageDigest does not have a nice textual
	 * representation, so some form of encoding is usually performed.
	 *
	 * This implementation follows the example of David Flanagan's book
	 * "Java In A Nutshell", and converts a byte array into a String of hex
	 * characters.
	 *
	 * Another popular alternative is to use a "Base64" encoding.
	 */
	static private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}

	public static boolean validateToken(String token) {

		return true;
	}

}
