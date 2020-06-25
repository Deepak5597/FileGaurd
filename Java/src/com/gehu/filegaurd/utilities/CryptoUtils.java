package com.gehu.filegaurd.utilities;
 
	import java.io.ByteArrayOutputStream;
	import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
	import java.security.Key;
	import java.security.NoSuchAlgorithmException;
	 
	import javax.crypto.BadPaddingException;
	import javax.crypto.Cipher;
	import javax.crypto.IllegalBlockSizeException;
	import javax.crypto.NoSuchPaddingException;
	import javax.crypto.spec.SecretKeySpec;
	 
	/**
	 * A utility class that encrypts or decrypts a file.
	 * @author www.codejava.net
	 *
	 */
	public class CryptoUtils {
		
		private final static String key = "UjXn2r5u8x!A%D*G";
	    private static final String ALGORITHM = "AES";
	    private static final String TRANSFORMATION = "AES";
	 
	    public static  byte[] encrypt(InputStream inputStream)
	            throws Exception { 
	        return doCrypto(Cipher.ENCRYPT_MODE, inputStream);
	    }
	 
	    public static byte[] decrypt(InputStream inputStream)
	            throws Exception {
	    	return doCrypto(Cipher.DECRYPT_MODE, inputStream);
	    }
	 
	    private static byte[] doCrypto(int cipherMode,InputStream inputStream) throws Exception {
	        try {
	            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
	            cipher.init(cipherMode, secretKey);
	            
	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	            int nRead;
	            byte[] data = new byte[16384];

	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	              buffer.write(data, 0, nRead);
	            }
	             
	            byte[] outputBytes = cipher.doFinal(buffer.toByteArray());
	            return outputBytes;
	        } catch (NoSuchPaddingException | NoSuchAlgorithmException
	                | InvalidKeyException | BadPaddingException
	                | IllegalBlockSizeException | IOException ex) {
	            throw new Exception("Error encrypting/decrypting file", ex);
	        }
	    }
}

