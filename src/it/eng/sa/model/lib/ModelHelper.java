package it.eng.sa.model.lib;

import it.eng.sa.util.Log;





//import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ModelHelper {
	
	
	public static String json(Model x) {
		try {
			return getMapper().writeValueAsString(x);
		} catch (Exception e) {
			Log.util.warn("json",e);
			return e.getMessage();
		}
	}

	public static String json(List<? extends Object> list) {
		if (list==null)
			return null;
		JSONArray a = new JSONArray();
		for(Object m:list) {
			if (m==null)
				a.put(JSONObject.NULL);
			else if (m instanceof Model)
				a.put(new JSONObject(json((Model) m)));
			else
				a.put(m);
		}
		return a.toString(2);
	}
	

	public static Object[] array(List<? extends Object> list) {
		if (list==null)
			return null;
		Object [] ret  = new Object[list.size()];
		for(int i=0;i<list.size();i++)
			ret[i] = list.get(i);
		return ret;
	}
	
	public static String jsonBeautify(String json) {
		JSONObject o = new JSONObject(json);
		return o.toString(2);
	}

	
	public static String jsonBeautify(Model model) {
		String j = json(model);
		JSONObject o = new JSONObject(j);
		return o.toString(2);
	}

//	public static String simpleJson(Model x) {
//		try {
//			JSONSerializer js = new JSONSerializer().exclude("*.class");
//			String json = js.deepSerialize(x);
//			
//			return json;
//		} 
//		catch (Exception e) {
//			Log.util.debug(e);
//			return ("ERROR: "+e.getMessage());
//		}
//	}
//
//	public static String simpleJsonString(Model x) {
//		try {
//			JSONSerializer js = new JSONSerializer().exclude("*.class");
//			String json = js.deepSerialize(x);
//			
//			return JSONObject.quote(json);
//		} 
//		catch (Exception e) {
//			Log.util.debug(e);
//			return ("ERROR: "+e.getMessage());
//		}
//	}

	
	public static <T> T createFromObject(Class<T> x, Object src) throws Exception {
		try {
			ObjectMapper mapper = getMapper();
			T t = x.newInstance();
			String j = mapper.writeValueAsString(t);
			return mapper.readValue(j, x);
		} 
		catch (Exception e) {
			Log.util.warn("ModelHelper.createFromObject", e);
			return null;
		}
	}

	public static <T extends Model> T  extend (T target, Model src) {
		return extend(target, json(src));
	}

	public static <T extends Model> T extend(T target, String jsrc) {
		try {
			ObjectMapper mapper = getMapper();
			mapper.readerForUpdating(target).readValue(jsrc);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return target;
	}

	public static <T extends Model> T  clone (T src) {
		ObjectMapper mapper = getMapper();
		try {
			String json = mapper.writeValueAsString(src);
			return (T) mapper.readValue(json,  src.getClass());
		} catch (Exception e) {
			Log.util.warn("ModelHelper.clone", e);
			return null;
		} 
	}
		
	
	public static <T extends Model> T fromJSON(String json, Class<T> modclass)  throws Exception {
		if (json==null)
			return null;
		T t = modclass.newInstance();
		return extend(t, json);
	}
	
	public static <T extends Model> List<T> fromJSONArray(String json, Class<T> modclass)  throws Exception {
		if (json==null)
			return null;
		List<T> ret = new ArrayList<T>();
		JSONArray a = new JSONArray(json);
		for(int i=0; i<a.length(); i++) {
			JSONObject o = a.getJSONObject(i);
			T t = modclass.newInstance();
			extend(t, o.toString());
			ret.add(t);
		}
		return ret;
	}
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
	private static String datePattern = dateFormat.format(new Date(0L));
	private static int dpLen = datePattern.length();
//	private static Charset UTF8 = Charset.forName("UTF-8");
	
//	private static JSONObject mergeObjects(JSONObject base, JSONObject merge) throws Exception {
//		// Clone the initial object (JSONObject doesn't support "clone").
//		String [] names = JSONObject.getNames(base);
//		JSONObject returned = (names!=null)
//				? new JSONObject(base, JSONObject.getNames(base))
//				: new JSONObject();
//		
//		// Walk parameter list for the merged object and merge recursively.
//		String[] fields = JSONObject.getNames(merge);
//		if (fields==null)
//			return returned;
//		
//		for (String field : fields) {
//			Object existing = returned.opt(field);
//			Object mergeField = merge.get(field);
//			if (existing==null || returned.isNull(field))  {
//				//if (mergeField!=null)
//					returned.put(field, mergeField);
//			} 
//			else if (mergeField == null) {
//				// it's removing a pre-configured value.
//				returned.put(field, mergeField);
//			} 
//			else {
//				if (mergeField instanceof JSONObject && existing instanceof JSONObject) {
//					returned.put(field, mergeObjects((JSONObject)existing, (JSONObject)mergeField));
//				} else {
//					// Otherwise we just overwrite it.
//					returned.put(field, mergeField);
//				}
//			}
//		}
//		return returned;
//	}


	public static String date2String(Date d) {
		if (d==null)
			return "null";
		else
			return dateFormat.format(d);
	}

	public static Date string2Date(String s) {
		if (s==null)
			return null;
		int len = s.length();
		if (len > dpLen)
			s = s.substring(0, dpLen);
		else if (len < dpLen)
			s = s+datePattern.substring(len);
		try {
			return dateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ObjectMapper getMapper() {
		return new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
			.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
			.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false)
			.configure(SerializationFeature.INDENT_OUTPUT, true)
		;
	}


}
