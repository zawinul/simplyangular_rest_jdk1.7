package it.eng.sa.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypter {
	private final static int keylen = 24;
    private static final String algorithm = "DESede";
	private Key key;
	public Crypter(String chiaveInChiaro) {
		byte hash[] = UTF8.md5Bytes(chiaveInChiaro);
		byte keydata[] = new byte[keylen];

		for(int i=0;i<keylen;i++)
			keydata[i] = hash[i% hash.length];

	    key = new SecretKeySpec(keydata, 0, keylen, algorithm);		
	}

	public String encrypt(String input) {
	    try {
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte x[] = UTF8.bytes(input);
			byte[] y =  c.doFinal(x);
			return UTF8.toBase64(y);
		} 
	    catch (Exception e) {
			Log.util.warn("crypt", e);
			return null;
		}
	}
	
	public synchronized String decrypt(String input) {
	    try {
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.DECRYPT_MODE, key);
			byte x[] = Base64.decodeBase64(UTF8.bytes(input));
			byte[] y =  c.doFinal(x);
			return UTF8.string(y);
		} 
	    catch (Exception e) {
			Log.util.warn("crypt", e);
			return null;
		}
	}
	
	public synchronized  static void main(String args[]) throws Exception {
		String testo ="quarantaquattro gatti in fila per sei col resto di 2048 fattoriale";
		Crypter c = new Crypter("this is my key");
		String enc = c.encrypt(testo);
		String dec = c.decrypt(enc);
		System.out.println(testo+"\n"+enc+"\n"+dec);
	}

}
