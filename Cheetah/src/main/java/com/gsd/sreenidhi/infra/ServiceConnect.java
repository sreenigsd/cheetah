package com.gsd.sreenidhi.infra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.CheetahUtils;
import com.gsd.sreenidhi.utils.FileUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * This class is used to local browser and Server connectivity
 * 
 * @author Sreenidhi, Gundlupet
 *
 */
public class ServiceConnect {

	/**
	 * This method is used to initialize the Drivers for the Selenium nodes on the
	 * Server
	 * 
	 * @param capabilities
	 *            Desired Capabilities
	 * @param props
	 *            Properties
	 * @return WebDriver
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("rawtypes")
	public WebDriver getRemoteDriver(DesiredCapabilities capabilities, Properties props) throws CheetahException {
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Obtaining Remote Driver",
				Constants.LOG_INFO, false);

		URL server = null;
		WebDriver driver = null;
		@SuppressWarnings("rawtypes")
		AndroidDriver androiddriver;
		@SuppressWarnings("rawtypes")
		IOSDriver iosdriver;
		
		CheetahUtils CheetahUtils = new CheetahUtils();
		
		String remoteSeleniumHubIP = null;
		String remoteSeleniumServerUrl = null; 
	
		remoteSeleniumHubIP = CheetahUtils.getServerIP();
		
		if(remoteSeleniumHubIP==null) {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"\n!**********************************************************************!"
							+ "\n!**********************************************************************!"
							+ "\n!                  #####   Error Binding Selenium Hub      #####           !" 
							+ "\n!             Please Contact the Administrator for support          !"
							+ "\n!                                                                      !"
							+ "\n!**********************************************************************!"
							+ "\n!**********************************************************************!",
					Constants.LOG_FATAL);
			System.exit(1);
		}
		
		remoteSeleniumServerUrl = generateRemoteServerURL(remoteSeleniumHubIP);
		
		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"\n!**********************************************************************!"
						+ "\n!**********************************************************************!"
						+ "\n!                  #####     Binding Selenium Hub      #####           !" 
						+ "\n!             Server Mapping: " +  remoteSeleniumServerUrl +"          !"
						+ "\n!                                                                      !"
						+ "\n!**********************************************************************!"
						+ "\n!**********************************************************************!",
				Constants.LOG_INFO);
		
		
		
		try {
			if ("MOBILE".equalsIgnoreCase(props.getProperty("test.type"))) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Initializing Mobile Test Type",
						Constants.LOG_INFO, false);
				String appiumHub = CheetahEngine.configurator.executionConfigurator.getAppiumHub()==null? Constants.APPIUM_HUB :CheetahEngine.configurator.executionConfigurator.getAppiumHub();
				String appiumPort = CheetahEngine.configurator.executionConfigurator.getAppiumPort()==null? Constants.APPIUM_PORT : CheetahEngine.configurator.executionConfigurator.getAppiumPort();
				server = new URL("http://"+appiumHub.trim()+":"+appiumPort.trim()+"/wd/hub");
				if ("ANDROID".equalsIgnoreCase(props.getProperty("os.platform"))) {
					androiddriver = new AndroidDriver(server, capabilities);
					driver = androiddriver;
				} else {
					iosdriver = new IOSDriver(server, capabilities);
					driver = iosdriver;
				}
			} else if ("WEB".equalsIgnoreCase(props.getProperty("test.type"))) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Initializing Web Test Type",
						Constants.LOG_INFO, false);

				String browser = capabilities.getBrowserName();
				browser = browser.toUpperCase();

				switch (browser) {
				case "CHROME":
					ChromeOptions chromeOptions = getChromeCapabilities(capabilities); 
					capabilities.merge(chromeOptions);
					break;
				case "FIREFOX":
					FirefoxOptions firefoxOptions = getFirefoxCapabilities(capabilities);
					capabilities.merge(firefoxOptions);
					break;
				case "IE":
					InternetExplorerOptions ieOptions = getIECapabilities(capabilities);
					capabilities.merge(ieOptions);
					break;
				case "SAFARI":
					SafariOptions safariOptions = getSafariCapabilities(capabilities);
					capabilities.merge(safariOptions);
					break;
				case "EDGE":
					EdgeOptions edgeOptions = getEdgeCapabilities(capabilities);
					capabilities.merge(edgeOptions);
					break;
				case "OPERA":
					OperaOptions operaOptions = getOperaCapabilities(capabilities);
					capabilities.merge(operaOptions);
					break;
				default:
					InternetExplorerOptions ieDefOptions = getIECapabilities(capabilities);
					capabilities.merge(ieDefOptions);
					break;
				}
				
				server = new URL(remoteSeleniumServerUrl);
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Attempting Remote Driver connection...",Constants.LOG_INFO, false);
				driver = new RemoteWebDriver(server, capabilities);
				Thread.sleep(5);
			} else if ("WebService".equalsIgnoreCase(props.getProperty("test.type"))) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Initializing WebService Test Type",
						Constants.LOG_INFO, false);
				server = new URL(remoteSeleniumServerUrl);
			}else if ("Database".equalsIgnoreCase(props.getProperty("test.type"))) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Initializing Database Test Type",
						Constants.LOG_INFO, false);
				server = new URL(remoteSeleniumServerUrl);
			}else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Unknown Test Type",
						Constants.LOG_INFO, false);
			}
		} catch (MalformedURLException e) {
			CheetahEngine.logger.logMessage(e, this.getClass().getName(),
					"MalformedURLException:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		}
		return driver;
	}

	/**
	 * This method is used to initialize the drivers for local desktop testing
	 * 
	 * @param capabilities
	 *            Desired Capabilities
	 * @param props
	 *            Properties
	 * @return WebDriver
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public WebDriver getLocalDriver(DesiredCapabilities capabilities, Properties props) throws CheetahException {

		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Obtaining Local Driver", Constants.LOG_INFO,
				false);

		FileUtils fu = new FileUtils();
		WebDriver driver = null;
		
		String browser = capabilities.getBrowserName();
		browser = browser.toUpperCase();
		String testType = props.getProperty("test.type");

		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"Creating Local Driver for browser: " + browser, Constants.LOG_INFO, false);

		if ("WEB".equalsIgnoreCase(testType)) {
			final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
			System.setOut(new PrintStream(myOut));

			DriverService ds = null;

			switch (browser) {
			case "CHROME":
				ChromeOptions chromeOptions =  getChromeCapabilities(capabilities);
				String chromeDriverPath = CheetahEngine.configurator.driverConfigurator.getChromeDriverPath();
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver(chromeOptions);
				break;
			case "FIREFOX":
				FirefoxOptions  firefoxOptions = getFirefoxCapabilities(capabilities);
				String firefoxDriverPath = CheetahEngine.configurator.driverConfigurator.getFirefoxDriverPath();
				System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
				driver = new FirefoxDriver(firefoxOptions);
				break;
			case "IE":
				InternetExplorerOptions ieOptions =  getIECapabilities(capabilities);
				String ieDriverPath = CheetahEngine.configurator.driverConfigurator.getIeDriverPath();
				System.setProperty("webdriver.ie.driver", ieDriverPath);
				driver = new InternetExplorerDriver(ieOptions);
				break;
			case "SAFARI":
				SafariOptions safariOptions =  getSafariCapabilities(capabilities);
				driver = new SafariDriver(safariOptions);
				break;
			case "EDGE":
				EdgeOptions edgeOptions = getEdgeCapabilities(capabilities);
				String edgeDriverPath = CheetahEngine.configurator.driverConfigurator.getEdgeDriverPath();
			    System.setProperty("webdriver.edge.driver",edgeDriverPath); 
				driver = new EdgeDriver(edgeOptions);
				break;
			case "OPERA":
				OperaOptions operaOptions = getOperaCapabilities(capabilities);
				String operaDriverPath = CheetahEngine.configurator.driverConfigurator.getOperaDriverPath();
			    operaOptions.setBinary(operaDriverPath);
			    System.setProperty("webdriver.opera.driver",operaDriverPath);
				driver = new OperaDriver(operaOptions);
				break;
			default:
				InternetExplorerOptions ieDefOptions =  getIECapabilities(capabilities);
				driver = new InternetExplorerDriver(ieDefOptions);
				break;
			}

			final String standardOutput = myOut.toString();

		} else if ("MOBILE".equalsIgnoreCase(props.getProperty("test.type"))) {
			@SuppressWarnings("rawtypes")
			AndroidDriver androiddriver;
			URL server = null;
			CheetahEngine.logger.logMessage(null,this.getClass().getName(), "Initializing Mobile Test Type",
					Constants.LOG_INFO, false);
			try {
				String appiumHub = CheetahEngine.configurator.executionConfigurator.getAppiumHub()==null? Constants.APPIUM_HUB :CheetahEngine.configurator.executionConfigurator.getAppiumHub();
				String appiumPort = CheetahEngine.configurator.executionConfigurator.getAppiumPort()==null? Constants.APPIUM_PORT : CheetahEngine.configurator.executionConfigurator.getAppiumPort();
				server = new URL("http://"+appiumHub.trim()+":"+appiumPort.trim()+"/wd/hub");
			} catch (MalformedURLException e) {
				CheetahEngine.logger.logMessage(e, this.getClass().getName(),
						"MalformedURLException:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
						Constants.LOG_ERROR, true);
			}
			if ("ANDROID".equalsIgnoreCase(props.getProperty("os.platform"))) {
				androiddriver = new AndroidDriver(server, capabilities);
				driver = androiddriver;
			} else {
				driver = new IOSDriver(server, capabilities);
			}
		} else if ("WEBSERVICE".equalsIgnoreCase(props.getProperty("test.type"))
				|| "DATABASE".equalsIgnoreCase(props.getProperty("test.type"))) {
			// No Driver
		}
		CheetahEngine.logger.logMessage(null,this.getClass().getName(), "Driver: " + driver, Constants.LOG_INFO,
				false);
		return driver;
	}

	/**
	 * @param capabilities
	 *            DesiredCapabilities
	 * @return DesiredCapabilities
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private ChromeOptions getChromeCapabilities(DesiredCapabilities capabilities) throws CheetahException {
		FileUtils fu = new FileUtils();
		String downloadFilepath = "";

		if (CheetahEngine.props.getProperty("download.file.location") != null	) {
			fu.createDirectories(new File(CheetahEngine.props.getProperty("download.file.location")).getAbsolutePath());
			downloadFilepath = new File(CheetahEngine.props.getProperty("download.file.location")).getAbsolutePath();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Using download file property... ",
					Constants.LOG_INFO);
		} else {
			fu.createDirectories(
					new File("target" + File.separator + "downloads").getAbsolutePath());
			downloadFilepath = new File("target" + File.separator + "downloads").getAbsolutePath();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Using plan download directory... ",
					Constants.LOG_INFO);
		}

		File file = new File(downloadFilepath);
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Download File location: " + downloadFilepath,
				Constants.LOG_INFO);

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", file.getAbsolutePath());
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars");
		chromeOptions.addArguments("disable-extensions");
		chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.merge(capabilities);
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.setCapability("useAutomationExtension", false);
		return chromeOptions;
	}

	/**
	 * @param capabilities
	 *            DesiredCapabilities
	 * @return DesiredCapabilities
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private FirefoxOptions getFirefoxCapabilities(DesiredCapabilities capabilities) throws CheetahException {
		FileUtils fu = new FileUtils();
		String downloadFilepath = "";

		FirefoxProfile fxProfile = new FirefoxProfile();
		fxProfile.setPreference("browser.download.folderList", 2);
		fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		if (CheetahEngine.props.getProperty("download.file.location") != null
				) {
			fu.createDirectories(new File(CheetahEngine.props.getProperty("download.file.location")).getAbsolutePath());
			downloadFilepath = new File(CheetahEngine.props.getProperty("download.file.location")).getAbsolutePath();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Using download file property... ",
					Constants.LOG_INFO);
		} else {
			fu.createDirectories(
					new File("target" + File.separator + "downloads").getAbsolutePath());
			downloadFilepath = new File("target" + File.separator + "downloads").getAbsolutePath();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Using plan download directory... ",
					Constants.LOG_INFO);
		}
		fxProfile.setPreference("browser.download.dir", downloadFilepath);
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Download File location: " + downloadFilepath,
				Constants.LOG_INFO);
		// fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		firefoxOptions.merge(capabilities);
		firefoxOptions.setProfile(fxProfile);

		return firefoxOptions;
	}

	private InternetExplorerOptions getIECapabilities(DesiredCapabilities capabilities) {
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		ieOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
		ieOptions.merge(capabilities);
		return ieOptions;
	}

	private SafariOptions getSafariCapabilities(DesiredCapabilities capabilities) {
		SafariOptions safariOptions = new SafariOptions();
		safariOptions.merge(capabilities);
		return safariOptions;
	}

	private EdgeOptions getEdgeCapabilities(DesiredCapabilities capabilities) {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.merge(capabilities);
		return edgeOptions;
	}

	private OperaOptions getOperaCapabilities(DesiredCapabilities capabilities) {
		OperaOptions operaOptions = new OperaOptions();
		operaOptions.merge(capabilities);
		return operaOptions;
	}

	private String generateRemoteServerURL(String remoteHubIP) {
		return "http://"+remoteHubIP+":4444/wd/hub";
	}
	
}
