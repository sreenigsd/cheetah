package com.gsd.sreenidhi.utils;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Properties;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class SystemEnvironment {

	/**
	 * @param props
	 *            Properties
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void processEnvironment(Properties props) throws CheetahException {
		CheetahEngine.logger.logMessage(null, SystemEnvironment.class.getName(), "Attempting DB Update: Test System Environment", Constants.LOG_INFO);
		InetAddress networkId = NetworkUtils.getLocalHostLANAddress();
		String properties = "";
		@SuppressWarnings("unchecked")
		Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
		if(enums!=null) {
			while (enums.hasMoreElements()) {
				String key = enums.nextElement();
				String value = props.getProperty(key);
				properties = properties + key + " : " + value + "\n";
			}
		}else {
			properties="";
		}
		
		DBExecutor.recordEnvironment(properties, networkId);
		CheetahEngine.logger.logMessage(null, SystemEnvironment.class.getName(), "Process Environment Complete!", Constants.LOG_INFO);
	}
}
