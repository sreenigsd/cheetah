package com.gsd.sreenidhi.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * This is the Encryption and decryption for fabric framework - web app
 * functionality AES:Advanced Encryption Standard Algorithm
 * 
 * @author Sreenidhi, Gundlupet
 *
 */

public class AESEncryption extends CheetahException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6880466273169443960L;
	private static SecretKeySpec secretKey;
	private static byte[] key;

	/**
	 * @param myKey Key
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static void setKey(String myKey) throws CheetahException {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			CheetahEngine.logger.logMessage(e, "AESEncryption",
					"NoSuchAlgorithmException:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (UnsupportedEncodingException e) {
			CheetahEngine.logger.logMessage(e, "AESEncryption",
					"UnsupportedEncodingException:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
	}

	/**
	 * @param strToEncrypt String to Encrypt
	 * @param secret Secret
	 * @return encrypted String
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static String encrypt(String strToEncrypt, String secret) throws CheetahException {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "AESEncryption",
					"Exception - Encoding Error:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
		return null;
	}

	/**
	 * @param strToDecrypt String to decrypt
	 * @param secret secret
	 * @return Decrypted String
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static String decrypt(String strToDecrypt, String secret) throws CheetahException {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "AESEncryption",
					"Exception - Decoding Error:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
		return null;
	}
	
	/**
	 * @param args arguments
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private static void main(String args[]) throws CheetahException {
		System.out.println(encrypt("username","secret"));
		//System.out.println(decrypt("",""));
	}
}