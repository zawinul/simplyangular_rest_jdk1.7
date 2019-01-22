package it.eng.sa.web;

import it.eng.sa.model.UtenteLoggato;
import it.eng.sa.rest.lib.Amministratore;
import it.eng.sa.util.Log;
import it.eng.sa.util.UTF8;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sun.org.apache.xerces.internal.util.Status;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();

		if (method.isAnnotationPresent(Amministratore.class)) {
			UtenteLoggato u = new SessionManager(request).getUtenteLoggato();
			if (u==null || !u.role.equals("admin")) {
				Log.rest.warn("refused!!!!! ");
				Response r = Response.status(Response.Status.FORBIDDEN)
			              .entity("Questa funzione è accessibile solo agli amministratori")
			              .build();
				throw new WebApplicationException(r);
			}
		} 
	}
	
	private static Key key;
    private static String algorithm = "DESede";
	static {
	    byte kbytes[] = Base64.decodeBase64(UTF8.bytes("gP0yqyNGirNKBCz3kZGDoUPBnu9tQN+X"));
	    key = new SecretKeySpec(kbytes, 0, kbytes.length, algorithm);		
	}
	
	static void prova(String testo) throws Exception {
	    
	    Cipher c = Cipher.getInstance(algorithm);
	    byte[] encryptionBytes = encryptF(testo,key,c);
	    String encripted = UTF8.toBase64(encryptionBytes);
	    String decripted = decryptF(encryptionBytes,key,c);
	    System.out.println("Encrypted: " + encripted);
	    System.out.println("Decrypted: " + decripted);
	}
	
	private static byte[] encryptF(String input,Key k,Cipher c) throws InvalidKeyException, BadPaddingException,IllegalBlockSizeException {
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] inputBytes = input.getBytes();
		return c.doFinal(inputBytes);
	}
	
	private static String decryptF(byte[] encryptionBytes,Key k,Cipher c) throws InvalidKeyException,BadPaddingException, IllegalBlockSizeException {
		c.init(Cipher.DECRYPT_MODE, k);
		byte[] decrypt = c.doFinal(encryptionBytes);
		String decrypted = new String(decrypt);
	
		return decrypted;
	}

	public static void main(String args[]) throws Exception {
		prova("quarantaquattro gatti in fila per sei col resto di 2048 fattoriale");
	}
}