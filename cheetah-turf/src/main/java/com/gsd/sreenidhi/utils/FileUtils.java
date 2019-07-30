package com.gsd.sreenidhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Gundlupet Sreenidhi
 *
 */
public class FileUtils {
	
	/**
	 * @param propFile
	 *            Name of Property File
	 * @return Properties
	 */
	public Properties getProperties(String propFile) {
		Properties properties = null;

		try {
			
			properties = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			StringBuffer path = new StringBuffer("properties" + File.separator + propFile);
			
			//InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(path.toString());
			InputStream in = getClass().getResourceAsStream(path.toString());
			if (in != null) {
				//File not found! (Manage the problem)
				properties.load(in);
			}else {
				System.out.println("Properties file not found...");
			}
			//properties.load(new FileInputStream(path.toString()));
			
			System.out.println("Loading property: " + path.toString() + " - " + properties.hashCode());
			if (properties.isEmpty()) {
				System.out.println("Unable to load the properties file");
			} else {
				System.out.println("Properties file loaded successfully");
			}
		} catch (IOException e) {
			System.out.println(" Error reading Property file " + "\n" + e.getLocalizedMessage());
		}
		return properties;
	}

}
