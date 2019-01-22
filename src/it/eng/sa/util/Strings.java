package it.eng.sa.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class Strings {
	
	public static String readerToString(Reader r) {
		StringBuffer b = new StringBuffer();
		final int BUFSIZE = 4096;
		char buf[] = new char[BUFSIZE];
		try {
			while(true) {
				int n = r.read(buf);
				if (n<=0)
					break;
				b.append(buf,0,n);
			}
			r.close();
		} 
		catch (IOException e) {
			Log.util.debug("", e);
		}
		return b.toString();
	}
	
	public static String streamToString(InputStream is) {
		return (is==null) ? null : readerToString(new InputStreamReader(is, Charset.forName("UTF-8")));
	}

	public static String resourceToString(String resourceName) {
		InputStream is = Strings.class.getClassLoader().getResourceAsStream(resourceName);
		return (is==null) ? null : streamToString(is);
	}


	public static String fileToString(String filePath) {
		try {
			return readerToString(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			Log.util.debug("Tentativo di leggere file che non esiste: "+filePath);
			return null;
		}
	}

//	public static String encodeBase64(byte [] data) {
//		if (data==null)
//			return null;
//		byte[] out = Base64.encodeBase64(data);
//		return new String(out, UTF8);
//	}

	
//	public static byte[] decodeBase64(String data) {
//		if (data==null)
//			return null;
//		return Base64.decodeBase64(data.getBytes(UTF8));
//	}

}
