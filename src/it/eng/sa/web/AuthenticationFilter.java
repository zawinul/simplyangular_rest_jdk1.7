package it.eng.sa.web;

import it.eng.sa.model.UtenteLoggato;
import it.eng.sa.model.lib.Model;
import it.eng.sa.model.lib.ModelHelper;
import it.eng.sa.rest.lib.Amministratore;
import it.eng.sa.util.Crypter;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.org.apache.xerces.internal.util.Status;

import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest request;

    private static String HASHSECRET = "D-G£/DJ)DJS/ 8";
	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		UtenteLoggato user = getUser(request);

		if (method.isAnnotationPresent(Amministratore.class)) {
			if (user==null || !user.role.equals("admin")) {
				Log.rest.warn("refused!!!!! ");
				Response r = Response.status(Response.Status.FORBIDDEN)
			              .entity("Questa funzione è accessibile solo agli amministratori")
			              .build();
				throw new WebApplicationException(r);
			}
		} 
	}

	private UtenteLoggato getUser(HttpServletRequest req) {
		try {
			UtenteLoggato user = (UtenteLoggato) request.getAttribute("utenteLoggato");
			if (user!=null)
				return user;

			user = new SessionManager(request).getUtenteLoggato();
			if (user!=null) {
				request.setAttribute("utenteLoggato", user);
				return user;
			}

			String token = request.getParameter("token");
			if (token==null) 
				token = request.getHeader("Authentication");
			
			if (token==null)
				return null;
			
			//String s = cripto.decrypt(token);
			String s = UTF8.fromBase64(token);
			String z[] = s.split("#");
			if (z.length<2) {
				Log.rest.warn("Token non riconosciuto");
				return null;								
			}
			String a=z[0], hash=z[1];
			String b = UTF8.md5(a+"#"+HASHSECRET);
			if (!b.equals(hash)) {
				Log.rest.warn("Token non riconosciuto");
				return null;				
			}
			String parts[] = a.split("\\|");
			if (parts.length<4) {
				Log.rest.warn("Token non riconosciuto");
				return null;								
			}
			long expire = Long.parseLong(parts[0]);
			if (expire<new Date().getTime()) {
				Log.rest.warn("Token for user "+parts[1]+" is expired");
				return null;				
			}
			user = new UtenteLoggato();
			user.cid = nullIfEmpty(parts[1]);
			user.role = nullIfEmpty(parts[2]);
			user.cdc = nullIfEmpty(parts[3]);
			request.setAttribute("utenteLoggato", user);
			return user;
		} 
		catch (Exception e) {
			Log.rest.warn("getUser", e);
			return null;
		}
	}
	
	private static String emptyIfNull(String x) {
		return (x==null) ? "" : x;
	}
	
	private static String nullIfEmpty(String x) {
		return (x==null) ? null : ((x.trim().equals("")?null:x));
	}
	
	public static String getToken(HttpServletRequest req) throws Exception {
		long expire = new Date().getTime()+((long) 1000*60*60); // 1 ora a partire da ora
		UtenteLoggato user = new SessionManager(req).getUtenteLoggato();
		if (user==null) // non sei loggato
			return null;

		String t = expire
			+ "|" + emptyIfNull(user.cid)
			+ "|" + emptyIfNull(user.role)
			+ "|" + emptyIfNull(user.cid);
		
		String s = UTF8.md5(t+"#"+HASHSECRET);	
		//String e = cripto.encrypt(t);
		String e = UTF8.toBase64(t+"#"+s);
		return e;
	}
}