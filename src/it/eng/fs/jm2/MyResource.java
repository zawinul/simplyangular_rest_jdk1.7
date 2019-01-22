package it.eng.fs.jm2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("esempio")
    public Esempio getIt2() {
        Esempio esempio = new Esempio();
        return esempio;
    }
    
    public static class Esempio {
    	public int x=3;
    	public boolean f=true;
    }
}
