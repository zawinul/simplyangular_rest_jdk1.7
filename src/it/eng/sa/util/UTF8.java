package it.eng.sa.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class UTF8 {
	private final static String utf8 = "UTF-8";
	public static String toBase64(String str) {
		if (str==null)
			return null;
		try {
			byte x[] = str.getBytes(utf8);
			byte y[] = Base64.encodeBase64(x);
			
			return new String(y, utf8);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String toBase64(byte[] x) {
		if (x==null)
			return null;
		try {
			byte y[] = Base64.encodeBase64(x);
			return new String(y, utf8);
		} catch (Exception e) {
			return null;
		}
	}

	public static String fromBase64(String str) {
		if (str==null)
			return null;
		try {
			byte x[] = str.getBytes(utf8);
			byte y[] = Base64.decodeBase64(x);
			
			return new String(y, utf8);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String string(byte[] b) {
		try {
			return new String(b, utf8);
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] bytes(String b) {
		try {
			return b.getBytes(utf8);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String urlEncode(String x) {
		try {
			return URLEncoder.encode(x, utf8);
		} catch (Exception e) {
			return null;
		}
	}

	public static String streamToString(InputStream is) {
		try {
			return IOUtils.toString(is, utf8);
		} 
		catch (IOException e) {
			return "Error: "+e.getMessage();
		} 
	}
	
	public static String md5(String text) {
		return md5(bytes(text));
	}
	
	public static String md5(byte bytes[]) {
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(bytes);
	}
	
	public static byte[] md5Bytes(String x) {
		return md5Bytes(bytes(x));
	}

	public static byte[] md5Bytes(byte bytes[]) {
		return org.apache.commons.codec.digest.DigestUtils.md5(bytes);
	}
	

}
