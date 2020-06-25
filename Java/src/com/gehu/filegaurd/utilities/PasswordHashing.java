package com.gehu.filegaurd.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordHashing {
	
	private static String salt="9eUxFPiL66qNHZum";
	
	public static String hashPassword(String password) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(new StringBuilder(salt).toString().getBytes());
		byte[] messageDigest = md.digest(password.getBytes()); 
		BigInteger no = new BigInteger(1, messageDigest); 
        String hashtext = no.toString(16);
		return hashtext;
	}
	
}
