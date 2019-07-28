package com.gsd.sreenidhi.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class Base64Encoding {

	/**
	 * @param args Arguments for main method 
	 */
	public static void main(String[] args){
		String encoded = encode("","");
		System.out.println("Encoded String: "+encoded);
	}

	/**
	 * Method to implement Base64 encoding for credentials
	 * @param username UserName
	 * @param password Password
	 * @return Encrypted String
	 */
	public static String encode(String username, String password){
		String login = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(login.getBytes());
		String encoded = new String(authEncBytes);
	
		return encoded;
	}

}
