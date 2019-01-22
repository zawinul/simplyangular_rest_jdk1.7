package it.eng.sa.web;

import it.eng.sa.model.Configuration;
import it.eng.sa.model.lib.ModelHelper;
import it.eng.sa.util.Log;
import it.eng.sa.util.Strings;

public class ConfigurationHelper {

	public static Configuration get() {
		if (singleton==null) {
			singleton = new Configuration();
			String jconf = Strings.resourceToString("configuration.json");
			if (jconf!=null)
				ModelHelper.extend(singleton, jconf);
			if (System.getProperty("configurationPatch")!=null)
				ModelHelper.extend(singleton, Strings.resourceToString(System.getProperty("configurationPatch")));
			Log.util.debug("CONFIG: "+singleton);
		}
		return singleton;
	}

	
	public static void refresh() {
		singleton = null;
	}
	
	private static Configuration singleton = null;
}
