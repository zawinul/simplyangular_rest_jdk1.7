package it.eng.sa.rest;

import it.eng.sa.model.DataSampleItem;
import it.eng.sa.model.lib.ModelHelper;
import it.eng.sa.rest.lib.Amministratore;
import it.eng.sa.util.Strings;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("data-sample")
public class DataSample {
	

	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<DataSampleItem> getDb() {
		return db;		
	}
	
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public DataSampleItem geItemById(@PathParam("id") int id) {
		
		for(DataSampleItem dsi: db)
			if (dsi.id==id)
				return dsi;
		
		throw new WebApplicationException(404); // not found
	}

	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Amministratore
	public DataSampleItem insertNew(DataSampleItem newItem) {
		newItem.id = nextId();
		db.add(newItem);
		return newItem;
	}

	@DELETE
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public DataSampleItem remove(@PathParam("id") int id) {
		for(DataSampleItem dsi: db) {
			if (dsi.id==id) {
				db.remove(dsi);
				return dsi;
			}
		}
		throw new WebApplicationException(404); // not found
	}

	@PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataSampleItem createOrUpdate(DataSampleItem newItem) {

		// create
		if (newItem.id == -1)
			return insertNew(newItem);
		
		// update
		DataSampleItem target = find(newItem.id);
		if (target==null)
			throw new WebApplicationException(404);
		
		ModelHelper.extend(target, newItem);
		return target;
	}

	
	// private-------------------------------------------------------------------------
	private int nextId() {
		int maxId = 0;
		for(DataSampleItem dsi: db)
			if (dsi.id>maxId)
				maxId = dsi.id;
		return maxId+1;
	}
	
	private DataSampleItem find(int id) {
		for(DataSampleItem dsi: db)
			if (dsi.id==id)
				return dsi;
		return null;
	}
	
	private static List<DataSampleItem> db;
	static {
		try {
			String json = Strings.resourceToString("initDB.json");
			db = ModelHelper.fromJSONArray(json, DataSampleItem.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
