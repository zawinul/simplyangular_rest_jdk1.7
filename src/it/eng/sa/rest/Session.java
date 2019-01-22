package it.eng.sa.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import it.eng.sa.model.ClientConfiguration;
import it.eng.sa.model.Configuration;
import it.eng.sa.model.UtenteLoggato;
import it.eng.sa.web.ConfigurationHelper;
import it.eng.sa.web.SessionManager;

@Path("session")
public class Session   {
	    
	@Context
    private HttpServletRequest request;
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("configuration")
    public ClientConfiguration getconf() {
		ClientConfiguration conf = new ClientConfiguration();
		Configuration config = ConfigurationHelper.get(); 
		conf = new ClientConfiguration();
		conf.debug = config.isDebug;
		conf.appLabel = config.appLabel;
		conf.user = new it.eng.sa.web.SessionManager(request).getUtenteLoggato();
		return conf;
	}
	
	@GET
    @Produces(MediaType.TEXT_PLAIN) 
	@Path("logout")
	public String logout() {
		request.getSession().invalidate();
		return "ok";
	}

	@POST
	//@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON) 
	@Path("login")
	public UtenteLoggato login(@FormParam("user") String user, @FormParam("password") String password) {
		SessionManager s = new SessionManager(request);
		return s.login(user, password);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON) 
	@Path("login2")
	public UtenteLoggato login2(LoginInput x) {
		SessionManager s = new SessionManager(request);
		return s.login(x.user, x.password);
	}

	public static class LoginInput {
		public String user;
		public String password;
	}

}
