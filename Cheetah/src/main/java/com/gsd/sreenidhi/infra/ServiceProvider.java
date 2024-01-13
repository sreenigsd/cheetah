package com.gsd.sreenidhi.infra;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.engine.CheetahForm;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ServiceProvider {

	protected static WebDriver driver = null;
	protected static String overrideCapabilities;

	/**
	 * @param props Properties
	 * @return WebDriver
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public WebDriver getDriver(Properties props) throws CheetahException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Execution Environment: " + CheetahEngine.configurator.executionConfigurator.getExecutionEnv(),
				Constants.LOG_INFO, false);


		String testType = props.getProperty("test.type");
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Test type: " + testType, Constants.LOG_INFO,
				false);

		if ("WEB".equalsIgnoreCase(testType)) {
			setSystemProxy();

			String browser = props.getProperty("browser.name");
			browser = browser.toUpperCase();
			String osPlatform = props.getProperty("os.platform");
			String osPlatformVersion = props.getProperty("os.platform.version");
			String browserVersion = props.getProperty("browser.version");
		
			if ("WINDOWS".equalsIgnoreCase(osPlatform)) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Browser: " + browser,
						Constants.LOG_INFO, false);

				capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				// Create proxy class object

				capabilities.setPlatform(Platform.WINDOWS);
				switch (browser) {
				case Constants.CHROME_BROWSER:
					capabilities.setBrowserName("chrome");
					break;
				case Constants.FIREFOX_BROWSER:
					capabilities.setBrowserName("firefox");
					break;
				case Constants.EDGE_BROWSER:
					capabilities.setBrowserName("edge");
					break;
				case Constants.OPERA_BROWSER:
					capabilities.setBrowserName("opera");
					break;
				default:
					capabilities.setBrowserName("chrome");
					break;
				}

			} else if ("MAC".equalsIgnoreCase(osPlatform)) {
				capabilities.setPlatform(Platform.MAC);
				switch (browser) {
				case Constants.CHROME_BROWSER:
					capabilities.setBrowserName("chrome");
					break;
				case Constants.FIREFOX_BROWSER:
					capabilities.setBrowserName("firefox");
					break;
				case Constants.SAFARI_BROWSER:
					capabilities.setBrowserName("safari");
					break;
				case Constants.OPERA_BROWSER:
					capabilities.setBrowserName("opera");
					break;
				default:
					capabilities.setBrowserName("safari");
					break;
				}
				capabilities.setPlatform(Platform.MAC);
			} else if ("LINUX".equalsIgnoreCase(osPlatform)) {
				capabilities.setPlatform(Platform.LINUX);
				switch (browser) {
				case Constants.CHROME_BROWSER:
					capabilities.setBrowserName("chrome");
					break;
				case Constants.FIREFOX_BROWSER:
					capabilities.setBrowserName("firefox");
					break;
				case Constants.OPERA_BROWSER:
					capabilities.setBrowserName("opera");
					break;
				default:
					capabilities.setBrowserName("firefox");
					break;
				}
			}
		} else if ("MOBILE".equalsIgnoreCase(testType)) {
			FileUtils fu = new FileUtils();
			if (("iOS").equalsIgnoreCase(props.getProperty("os.platform"))) {
				capabilities.setCapability("platformName", props.getProperty("os.platform"));
				capabilities.setCapability("platformVersion", props.getProperty("os.platform.version"));
				capabilities.setCapability("deviceName", props.getProperty("device.name"));
				capabilities.setCapability("orientation", props.getProperty("device.orientation"));
				capabilities.setCapability("automationName", props.getProperty("automation.name"));
				capabilities.setCapability("app", fu.getMobileApp(props.getProperty("automation.app")));
			} else if (("Android").equalsIgnoreCase(props.getProperty("os.platform"))) {
				capabilities.setCapability("platformName", props.getProperty("os.platform"));
				capabilities.setCapability("platformVersion", props.getProperty("os.platform.version"));
				capabilities.setCapability("deviceName", props.getProperty("device.name"));
				capabilities.setCapability("automationName", props.getProperty("automation.name"));
				capabilities.setCapability("app", fu.getMobileApp(props.getProperty("automation.app")));
			}
		} else if ("WebService".equalsIgnoreCase(testType)) {
			setSystemProxy();

		} else if ("Database".equalsIgnoreCase(testType)) {

		}

		if (CheetahEngine.configurator.executionConfigurator.getExecutionEnv()!=null
				&& "localhost".equalsIgnoreCase(CheetahEngine.configurator.executionConfigurator.getExecutionEnv())) {
			ServiceConnect serviceConnect = new ServiceConnect();
			driver = serviceConnect.getLocalDriver(capabilities, props);
		} else {
			if (CheetahEngine.configurator.executionConfigurator.getExecutionEnv() != null && "SAUCELABS".equalsIgnoreCase(CheetahEngine.configurator.executionConfigurator.getExecutionEnv())) {
				SauceConnect sc = new SauceConnect();
				sc.getDriver(capabilities, props);
			} else if (CheetahEngine.configurator.executionConfigurator.getExecutionEnv() != null
					&& Constants.GRID_CONNECTOR.equalsIgnoreCase(CheetahEngine.configurator.executionConfigurator.getExecutionEnv())) {
				ServiceConnect serviceConnect = new ServiceConnect();
				driver = serviceConnect.getRemoteDriver(capabilities, props);
			} else {
				Exception e = new Exception("Failed to retrieve Execution environment. Will attempt to execute in localhost...");
				CheetahEngine.logger.logMessage(e, this.getClass().getName(), e.getMessage(),Constants.LOG_WARN, true);
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Attempting local execution",Constants.LOG_INFO, false);
				ServiceConnect serviceConnect = new ServiceConnect();
				driver = serviceConnect.getLocalDriver(capabilities, props);
			}
		}

		if ("WEB".equalsIgnoreCase(testType)) {
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Constants.GLOBAL_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(Constants.GLOBAL_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().window().maximize();

			if (CheetahEngine.props.getProperty("screen.resolution") != null) {
				String d = CheetahEngine.props.getProperty("screen.resolution");
				String d1 = d.substring(0, d.indexOf('x'));
				String d2 = d.substring(d.indexOf('x') + 1);

				driver.manage().window().setSize(new Dimension(Integer.parseInt(d1), Integer.parseInt(d2)));
				driver.manage().window().setPosition(new Point(0, 0));
			}

			try {
				// Setting Javascript zoom level for IE browser.
				if (CheetahEngine.props.getProperty("browser.zoom.level") != null) {
					if ("IE".equalsIgnoreCase(props.getProperty("browser.name"))) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("document.body.style.zoom=\""
								+ CheetahEngine.props.getProperty("browser.zoom.level") + "\"");
					}
				}

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new CheetahException(e);
			} catch (JavascriptException jse) {
				throw new CheetahException(jse);
			}

		}

		CheetahForm.driver = driver;
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Created Driver:" + driver, Constants.LOG_INFO,
				false);

		return driver;
	}

	/**
	 * @param constantValue
	 * @param propertyValue
	 * @param overrideCapabilities2
	 * @return
	 */
	private String validateCapability(String constantValue, String propertyValue, String overrideCapabilities) {
		String val = "";
		if ("TRUE".equalsIgnoreCase(overrideCapabilities) && propertyValue != null) {
			val = propertyValue.toUpperCase();
			val = val.trim();
		} else if (constantValue != null) {
			val = constantValue.toUpperCase();
			val = val.trim();
		} else {
			// Property Error
		}
		val = CheetahEngine.configurator.executionConfigurator.getExecutionEnv();
		return val;
	}

	private void setSystemProxy() {
		// Implemented in frameworkOps
	}
}
