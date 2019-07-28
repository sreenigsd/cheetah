package com.gsd.sreenidhi.infra;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * This class is used for SauceLabs connectivity
 * 
 * @author Sreenidhi, Gundlupet
 *
 */
public class SauceConnect {

	/**
	 * This method is used to initialize the Remote Web Driver for Saucelabs
	 * 
	 * @param capabilities
	 *            Desired Capabilities
	 * @param props
	 *            Properties
	 * @return WebDriver
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public WebDriver getDriver(DesiredCapabilities capabilities, Properties props) throws CheetahException {

		WebDriver driver = null;

		String username = props.getProperty("sauceLabsUsername");
		String accessKey = props.getProperty("sauceLabsAccessKey");
		try {
			CheetahEngine.logger.logMessage(null, "SauceConnect",
					"Attempting connection:" + "http://" + username + ":" + accessKey + "@localhost:4445/wd/hub",
					Constants.LOG_INFO, false);
			driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@localhost:4445/wd/hub"),
					capabilities);
		} catch (MalformedURLException e) {
			CheetahEngine.logger.logMessage(e, "SauceConnect",
					"MalformedURLException:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		}
		return driver;
	}

}
