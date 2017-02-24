package com.cqut.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy
public class SystemUtil {

	private static Map<String, String> system = null;
	private static Properties prop = null;
	private static final String PROPERTIES_PATH;

	static {
		String folderPath = SystemUtil.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();
		String path = null;
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0,
					folderPath.indexOf("WEB-INF/classes")).replaceAll("%20",
					" ");
			path += "WEB-INF/classes/systemParameters.properties";
		}
		PROPERTIES_PATH = path;
		init();
	}


	private static void init() {
		try {
			prop = new Properties();
			FileInputStream files = new FileInputStream(PROPERTIES_PATH);
			prop.load(files);
			files.close();
		} catch (FileNotFoundException e) {
			//
		} catch (IOException e) {
			//
		}
	}

	public static void reload() {
		init();
	}

	@RemoteMethod
	public static Map<String, String> getSystemParameters() {
		system = new HashMap<String, String>();
		for (Object t : prop.keySet()) {
			system.put(t.toString(), prop.getProperty(t.toString()));
		}
		return system;
	}

	@RemoteMethod
	public static String getSystemParameter(String key) {
		try {
			return prop.getProperty(key);
		} catch (Exception e) {
			//
		}
		return null;
	}

	/*
	 * 返回basePath
	 */
	@RemoteMethod
	public static String getBasePath() {
		try {
			return Thread.currentThread().getContextClassLoader().getResource(
					"").toString().substring(6);
		} catch (Exception e) {
			//
		}
		return null;
	}

	//修改properties
	@RemoteMethod
	public static boolean writePro(String key, String value) {
		try {
			FileOutputStream fos = new FileOutputStream(PROPERTIES_PATH);
			prop.setProperty(key, value);
			prop.store(fos, "update '" + key + "' value");
			fos.close();
			init();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
	}
}
