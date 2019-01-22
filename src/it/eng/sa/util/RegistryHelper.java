package it.eng.sa.util;

import it.eng.sa.model.lib.Model;
import it.eng.sa.model.lib.ModelHelper;

import java.util.List;

public class RegistryHelper  { 
	private static RegistryHelperStorage storage;
	
	public  static <T extends Model> T get(String key, Class<T> modclass)    {
		String json = get(key);
		if (json==null)
			return null;
		try {
			return ModelHelper.fromJSON(json, modclass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String key)   {
		Log.db.debug("Registry get "+key);
		try {
			return storage.get(key); 
		} 
		catch (Exception e) {
			Log.db.warn("", e);
			return null;
		}
	}
	
	public static boolean set(String key, String value)  {
		try {
			return storage.put(value, value);
		} 
		catch(Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

	public static  boolean set(String key, Model data)   {
		return set(key, data.toString());
	}
	
	public static   boolean delete(String key)   {
		// TODO
		return false;
	}
	
	public static interface RegistryHelperStorage {
		boolean put(String key, String value) throws Exception;
		String get(String key) throws Exception;
		List<String> keys() throws Exception;
	}

}

