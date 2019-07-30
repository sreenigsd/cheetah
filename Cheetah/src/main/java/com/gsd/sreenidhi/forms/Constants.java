package com.gsd.sreenidhi.forms;

/**
 * Constants class
 * 
 * @author Sreenidhi, Gundlupet
 *
 */

public class Constants {

	
	public Constants(){
		// constructor.
	}
	
	/*
	 * Generic Constants
	 */
	public static final String frameworkProperties = "application.properties";
	public static final Boolean recordVideo = true;
	public static final Boolean splitVideoPerTest = true;
	public static final Boolean screenShotCapture = true;
	public static final Boolean screenShotOnFail = true;
	public static final Boolean screenShotOnPass = true;
	public static final boolean sendMail = true;
	public static final boolean highlight_element = true;
	public static final String TimeoutException = "It is an instance of a Timeout Exception";
	public static final int GLOBAL_TIMEOUT = 120;
	public static final char DEFAULT_SEPARATOR = ',';
	public static final String UTF_8_ENCODING = "UTF-8";
	public static final String LOG_FATAL = "fatal";
	public static final String LOG_ERROR = "error";
	public static final String LOG_INFO = "info";
	public static final String LOG_WARN = "warn";
	public static final String LOG_EXCEPTION = "exception";
	public static final String ORIGINAL_EXCEPTION_STRING = "Original Exception: \n";
	public static final int windowStackSize=10;
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_PUT = "PUT";
	
	/*
	 * Database Constants
	 */
	public static final String db2DriverClassname = "com.ibm.db2.jcc.DB2Driver";
	public static final String oracleDriverClassname = "oracle.jdbc.driver.OracleDriver";
	public static final String mySqlDriverClassname = "com.mysql.cj.jdbc.Driver";
	public static final String sybaseDriverClassname = "com.sybase.jdbc.SybDriver";
	public static final String sqlServerDriverClassname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	/*
	 * Browser and Driver Constants
	 */
	public static final String CHROME_BROWSER = "CHROME";
	public static final String FIREFOX_BROWSER = "FIREFOX";
	public static final String OPERA_BROWSER = "OPERA";
	public static final String EDGE_BROWSER = "EDGE";
	public static final String SAFARI_BROWSER = "SAFARI";
	public static final String IE_BROWSER = "IE";
	
	/*Default Driver names*/
	
	public static final String IEDriverName = "IEDriverServer.exe";
	public static final String ChromeDriverName = "chromedriver.exe";
	public static final String FirefoxDriverName = "geckodriver.exe";
	public static final String EdgeDriverName = "MicrosoftWebDriver.exe";
	public static final String OperaDriverName = "operadriver.exe";

	public static final String defaultTestType = "WEB";
	public static final String defaultBrowserName = "IE";
	public static final String defaultBrowserVersion = "12";
	public static final String defaultOSPlatform = "WINDOWS";
	public static final String defaultOSPlatformVersion = "7";
	
	/*
	 * Proxy Constants
	 */
	public static final boolean VALIDATE_URL_ON_SERVER = false;
	public static final boolean USE_SERVER_DIRECT_FIREWALL = false;
	
	/*
	 * Default Execution Constants
	 */
	public static final String LOCALHOST = "localhost";
	public static final String GRID_CONNECTOR = "terrain";
	public static final String SAUCELABS = "saucelabs";
	public static final String APPIUM_HUB = "0.0.0.0";
	public static final String APPIUM_PORT = "4723";
			

		
}
