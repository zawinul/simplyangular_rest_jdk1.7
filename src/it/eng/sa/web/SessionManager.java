package it.eng.sa.web;

import it.eng.sa.model.UtenteLoggato;
import it.eng.sa.util.Log;
import it.eng.sa.web.upload.UploadServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionManager {
	
	private HttpServletRequest req;
	public SessionManager(HttpServletRequest req) {
		this.req = req;
	}
	
	public void setUtenteLoggato(UtenteLoggato x) {
		req.getSession().setAttribute("utenteLoggato", x);
	}
	
	public UtenteLoggato getUtenteLoggato() {
		return (UtenteLoggato) req.getSession().getAttribute("utenteLoggato");
	}
	
	public UtenteLoggato login(String username, String password) {
		if (!password.equals("1234"))
			return null;
		UtenteLoggato x = new UtenteLoggato();
		x.cid = username;
		x.cdc = "aaaa";
		x.role = (username.startsWith("a")) ?"admin" : "clerk";
		setUtenteLoggato(x);
		return x;
	}
	
	//================================================================
	public static class SessionListener implements HttpSessionListener {
		static int count = 0;
		
		public void sessionCreated(HttpSessionEvent sessionEvent) {
			Log.web.debug("\nSESSIONSTARTED "+count+", id="+sessionEvent.getSession().getId());
			sessionEvent.getSession().setAttribute("sessionCount", count);
			count++;
			
			ConfigurationHelper.refresh();
		}

		public void sessionDestroyed(HttpSessionEvent sessionEvent) {
			Log.web.debug("\nSESSION END"+sessionEvent.getSession().getAttribute("sessionCount")+", id="+sessionEvent.getSession().getId());
			UploadServlet.cleanSession(sessionEvent.getSession().getId());
		}
	}
	
}
