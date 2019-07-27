package com.gsd.sreenidhi.cheetah.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gsd.sreenidhi.cheetah.actions.appium.AppiumActions;
import com.gsd.sreenidhi.cheetah.actions.selenium.SeleniumActions;
import com.gsd.sreenidhi.cheetah.actions.webService.WebServiceActions;
import com.gsd.sreenidhi.cheetah.data.XMLParser;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.cheetah.reporting.Media;
import com.gsd.sreenidhi.cheetah.reporting.ReportingBean;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.automation.config.Configurator;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.infra.ServiceProvider;
import com.gsd.sreenidhi.utils.FileUtils;

import cucumber.api.Scenario;

/**
 * This is the base class that can be extended to all applications
 * 
 * @author Sreenidhi, Gundlupet
 *
 */
public class CheetahEngine {

	public static Properties props;
	public static Log logger;
	public static Media media = null;
	public static XMLParser xmlParser = null;
	protected static WebDriver driver = null;
	public static CheetahForm cheetahForm = new CheetahForm();
	public static DesiredCapabilities capabilities = null;
	public static ReportingForm reportingForm = null;
	public static ArrayList<ReportingBean> reportingBeanList = null;

	protected static ServiceProvider serviceProvider = null;
	public static FileUtils fileUtils = null;
	protected static SeleniumActions seleniumActions = null;
	protected static AppiumActions appiumActions = null;
	protected static WebServiceActions webServiceActions = null;
	protected static FrameworkOps frameworkOps = null;

	public static String browserPort = null;
	public static ScreenRecorder screenRecorder;
	public static int nodePortNumber;
	public static String app_name;
	public static String original_style;
	public static HashMap hashMap;
	public static HashMap<String, String> dbDriversClassName = new HashMap<String, String>();

	public static Date testStartTime;
	public static Date testEndTime;

	public static String videoFileName;

	private static long idCounter = 0;
	public static boolean validDB = false;

	public static WindowHandleStack handleStack;
	
	public static Configurator configurator;

	/**
	 * @return ScenarioExecutionId
	 */
	public static synchronized String retrieveScenarioExecutionId() {
		return String.valueOf(idCounter++);
	}

	/**
	 * This function will be executed before execution of any other process
	 * 
	 * @param appName
	 *            Name of the application for which the test is being executed.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void runTestSuite(String appName) throws CheetahException {

		logger.logMessage(null, "CheetahEngine", "Initializing DB constants", Constants.LOG_INFO, false);

		dbDriversClassName.put("db2", Constants.db2DriverClassname);
		dbDriversClassName.put("sqlServer", Constants.sqlServerDriverClassname);
		dbDriversClassName.put("mySql", Constants.mySqlDriverClassname);
		dbDriversClassName.put("oracle", Constants.oracleDriverClassname);
		dbDriversClassName.put("sybase", Constants.sybaseDriverClassname);

		logger.logMessage(null, "CheetahEngine", "Initializing Cheetah Classes", Constants.LOG_INFO, false);
		serviceProvider = new ServiceProvider();
		xmlParser = new XMLParser();
		seleniumActions = new SeleniumActions();
		appiumActions = new AppiumActions();
		webServiceActions = new WebServiceActions();
		hashMap = new HashMap();
		frameworkOps = new FrameworkOps();

		logger.logMessage(null, "CheetahEngine", "Execution started.. from CheetahEngine.", Constants.LOG_INFO, false);
		// props = initializeProperties();

		FileUtils fu = new FileUtils();
		fu.printProperties(CheetahEngine.props);

		logger.logMessage(null, "CheetahEngine", "Property initialization complete.", Constants.LOG_INFO, false);

		checkhighlights();
		checkSplitVideo();
		
		initializeBase(props);

		logger.logMessage(null, "CheetahEngine", "Execution started.. Initialisation is done.", Constants.LOG_INFO,
				false);
		cheetahForm.setLogger(logger);
		cheetahForm.setProperties(props);
		cheetahForm.setDbDriversClassName(dbDriversClassName);

	}

	private static void checkhighlights() throws CheetahException {
		logger.logMessage(null, "CheetahEngine", "Checking Highlights functionality...", Constants.LOG_INFO, false);

		if (Constants.highlight_element && (CheetahEngine.props.getProperty("element.highlights") == null
				|| (CheetahEngine.props.getProperty("element.highlights") != null
						&& "TRUE".equalsIgnoreCase(CheetahEngine.props.getProperty("element.highlights"))))) {
			logger.logMessage(null, "CheetahEngine", "***** Highlights enabled... ***** ", Constants.LOG_INFO, false);
			cheetahForm.setHighlights(true);
		} else {
			logger.logMessage(null, "CheetahEngine", "***** Highlights disabled... ***** ", Constants.LOG_INFO, false);
			cheetahForm.setHighlights(false);
		}
	}

	public static boolean checkSplitVideo() throws CheetahException {
		boolean splitVid = false;
		if (Constants.splitVideoPerTest != null && Constants.splitVideoPerTest) {
			logger.logMessage(null, "CheetahEngine", "***** Cheetah Supports Video Capture split by test case... ***** ",
					Constants.LOG_INFO, false);
			cheetahForm.setSplitVideo(true);
			splitVid=true;
			if (CheetahEngine.props.getProperty("split.video") != null) {
				logger.logMessage(null, "CheetahEngine", "***** Validating user settings for Split video ***** ",
						Constants.LOG_INFO, false);

				if ("TRUE".equalsIgnoreCase((CheetahEngine.props.getProperty("split.video")))) {
					logger.logMessage(null, "CheetahEngine", "***** Video Capture will be split by test case... ***** ",
							Constants.LOG_INFO, false);
					cheetahForm.setSplitVideo(true);
					splitVid=true;
				} else if ("FALSE".equalsIgnoreCase((CheetahEngine.props.getProperty("split.video")))) {
					logger.logMessage(null, "CheetahEngine", "***** Video Capture will NOT be split by test case... Video captures entire session...***** ",
							Constants.LOG_INFO, false);
					cheetahForm.setSplitVideo(false);
					splitVid=false;
				} else {
					logger.logMessage(null, "CheetahEngine",
							"***** Did not find user input for split video... Using Cheetah settings for split video... ***** ",
							Constants.LOG_INFO, false);
					if (Constants.splitVideoPerTest != null && Constants.splitVideoPerTest) {
						logger.logMessage(null, "CheetahEngine",
								"***** Video Capture will be split by test case... ***** ", Constants.LOG_INFO, false);
						cheetahForm.setSplitVideo(true);
						splitVid=true;
					} else {
						logger.logMessage(null, "CheetahEngine",
								"***** Video Capture will NOT be split by test case... Video captures entire session...***** ", Constants.LOG_INFO,
								false);
						cheetahForm.setSplitVideo(false);
						splitVid=false;
					}
				}

			} else {
				logger.logMessage(null, "CheetahEngine",
						"***** Did not find user input for split video... Using Cheetah settings for split video... ***** ",
						Constants.LOG_INFO, false);
				if (Constants.splitVideoPerTest != null && Constants.splitVideoPerTest) {
					logger.logMessage(null, "CheetahEngine", "***** Video Capture will be split by test case... ***** ",
							Constants.LOG_INFO, false);
					cheetahForm.setSplitVideo(true);
					splitVid=true;
				} else {
					logger.logMessage(null, "CheetahEngine",
							"***** Video Capture will NOT be split by test case... Video captures entire session...***** ", Constants.LOG_INFO, false);
					cheetahForm.setSplitVideo(false);
					splitVid=false;
				}
			}

		} else {
			logger.logMessage(null, "CheetahEngine",
					"***** Cheetah does not support split video capabilities... Please contact your administrator... ***** ", Constants.LOG_INFO, false);
			cheetahForm.setSplitVideo(false);
			splitVid=false;
		}
		
		return splitVid;
	}

	/**
	 * This method is used to initialize the property files for the entire
	 * transaction.
	 * 
	 * @return Properties
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("rawtypes")
	public static Properties initializeProperties() throws CheetahException {
		logger.logMessage(null, "CheetahEngine", "Loading Properties file..", Constants.LOG_INFO, false);
		Properties properties = new Properties();
		Properties prop = new Properties();
		FileUtils fileUtils = new FileUtils();
		properties = fileUtils.getProperties(Constants.frameworkProperties);
		logger.logMessage(null, "CheetahEngine", "Properties file loaded successfully.", Constants.LOG_INFO,
				false);

		for (Enumeration propKeys = properties.propertyNames(); propKeys.hasMoreElements();) {
			// get the value, trim off the whitespace, then store it
			// in the received properties object.
			String tmpKey = (String) propKeys.nextElement();
			String tmpValue = properties.getProperty(tmpKey);
			tmpValue = tmpValue.trim();
			prop.put(tmpKey, tmpValue);
		}
		logger.logMessage(null, "CheetahEngine", "Properties have been successfully sanitized.", Constants.LOG_INFO,
				false);
		
		props = prop;
		return prop;

	}

	/**
	 * This method is used to Initialize all executions
	 * 
	 * @param props
	 *            Properties
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void initializeBase(Properties props) throws CheetahException {
		logger.logMessage(null, "CheetahEngine", "Initializing Base", Constants.LOG_INFO, false);

		try {
			frameworkOps.setSystemProxy(props);
		} catch (Exception e) {
			throw new CheetahException(e);
		}

		boolean validate = frameworkOps.validateLink(props);
		if (!validate) {
			logger.logMessage(null, "CheetahEngine", "Teminating Test. Please check the application URL.",
					Constants.LOG_INFO, false);
			System.exit(1);
		}

	//	frameworkOps.resetSystemProxy();
		driver = cheetahForm.getDriver();
		if (driver == null) {
			driver = createNewDriver(props);
			cheetahForm.setDriver(driver);
			System.out.println("Driver Set: " + driver);

		}
		logger.logMessage(null, "CheetahEngine", "Driver:  initialized successfully.", Constants.LOG_INFO, false);

		handleStack = new WindowHandleStack(Constants.windowStackSize);
	}

	/**
	 * This method is used to initialize a new WebDriver
	 * 
	 * @param props
	 *            Properties
	 * @return WebDriver
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	protected static synchronized WebDriver createNewDriver(Properties props) throws CheetahException {
		/**
		 * Delete all cookies at the start of each scenario to avoid shared state
		 * between tests
		 */
		logger.logMessage(null, CheetahEngine.class.getName(), "Creating new Driver", Constants.LOG_INFO, false);
		driver = serviceProvider.getDriver(props);
		return driver;
	}

	/**
	 * This function will be executed at the end of all execution
	 * 
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */

	public static void destroy() throws CheetahException {
		cheetahForm.setDriver(null);
		if (driver != null) {
			driver.quit();
		}
		logger.logMessage(null, "CheetahEngine", "Session Destroyed.", Constants.LOG_INFO, false);
	}

	/**
	 * Initialize the Cheetah Base
	 * 
	 * @param appName
	 *            Name of the application for which the test is being executed.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void generateBase(String appName) throws CheetahException {
		
		processVideoRecording();
		logger.logMessage(null, "CheetahEngine", "Generating Base", Constants.LOG_INFO, false);
		testStartTime = Calendar.getInstance().getTime();
		DBExecutor.recordNewScenario();

		runTestSuite(appName);
		try {
			// 5 second sleep after browser launch
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @return Log
	 */
	public Log getLogger() {
		return logger;
	}

	/**
	 * @param scenario
	 *            Scenario that was just executed.
	 * @param appName
	 *            Name of the application for which the test is being executed.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void processPostAction(Scenario scenario, String appName) throws CheetahException {
		testEndTime = Calendar.getInstance().getTime();
		ReportingBean bean = new ReportingBean();
		bean.setTest_Status(scenario.getStatus());
		if ("web".equalsIgnoreCase(props.getProperty("test.type"))) {
			bean.setTest_Page_URL((driver.getCurrentUrl() != null) ? driver.getCurrentUrl() : "");
		}
		bean.setScenario(scenario.getName());
		bean.setScenarioImpl(scenario);
		bean.setScenarioExecutionId(retrieveScenarioExecutionId());
		bean.setTestStartTime(testStartTime);
		bean.setTestEndTime(testEndTime);

		long diff = testEndTime.getTime() - testStartTime.getTime();// as given

		long executionTime = TimeUnit.MILLISECONDS.toMillis(diff);

		bean.setExecutionTime(executionTime);

		logger.logMessage(null, CheetahEngine.class.getName(), "Scenario complete:\n" + scenario.toString(),
				Constants.LOG_INFO, false);

		if (scenario.isFailed()) {

			reportingForm.setStatusFail(reportingForm.getStatusFail() + 1);
			try {
				scenario.write("Current Page URL is " + driver.getCurrentUrl());

				if (screenShotForTestType()) {
					if (Constants.screenShotCapture && Constants.screenShotOnFail) {
						// Take Screenshot on Failure
						WebDriver augmentedDriver = new Augmenter().augment(driver);
						byte[] screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
						scenario.embed(screenshot, "image/png");
						bean.setScreenshot(screenshot);
						DBExecutor.recordScreenshot(screenshot, Calendar.getInstance().getTime().toString(), "FAILURE",
								retrieveScenarioExecutionId() + "_TestFail");
					}
					logger.logMessage(null, CheetahEngine.class.getName(),
							"Screenshot Captured - " + driver.getCurrentUrl(), Constants.LOG_INFO, false);
				}

			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				logger.logMessage(null, CheetahEngine.class.getName(), somePlatformsDontSupportScreenshots.getMessage(),
						Constants.LOG_ERROR, false);
			}
		} else {
			reportingForm.setStatusPass(reportingForm.getStatusPass() + 1);
			if (Constants.screenShotCapture && Constants.screenShotOnPass) {
				try {
					if (props.getProperty("screenshot.on.success") != null
							&& "TRUE".equalsIgnoreCase(props.getProperty("screenshot.on.success"))) {
						if (screenShotForTestType()) {
							// Take Screenshot on Success
							WebDriver augmentedDriver = new Augmenter().augment(driver);
							byte[] screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
							scenario.embed(screenshot, "image/png");
							bean.setScreenshot(screenshot);
							DBExecutor.recordScreenshot(screenshot, Calendar.getInstance().getTime().toString(),
									"FAILURE", retrieveScenarioExecutionId() + "_TestPass");

							logger.logMessage(null, CheetahEngine.class.getName(),
									"Screenshot Captured - " + driver.getCurrentUrl(), Constants.LOG_INFO, false);
						}
					}
				} catch (WebDriverException somePlatformsDontSupportScreenshots) {
					logger.logMessage(null, CheetahEngine.class.getName(),
							somePlatformsDontSupportScreenshots.getMessage(), Constants.LOG_ERROR, false);
				}

			}
		}

		reportingBeanList.add(bean);

		if (CheetahEngine.props.getProperty("close.browser") != null && "FALSE".equalsIgnoreCase(CheetahEngine.props.getProperty("close.browser"))) {
			logger.logMessage(null, CheetahEngine.class.getName(), "Browser remains Open", Constants.LOG_INFO, false);
			cheetahForm.setDriver(driver);
		} else {
			logger.logMessage(null, CheetahEngine.class.getName(), "Browser Close", Constants.LOG_INFO, false);
			CheetahEngine.destroy();
			try {
				// 5 second sleep between each browser session
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new CheetahException(e);
			}

		}

		processProperty(appName);
		logger.breakLine();
		DBExecutor.updateScenario(scenario, bean);
		processVideoCompletion(scenario);

	}

	private static boolean screenShotForTestType() {
		if ("WEB".equalsIgnoreCase(props.getProperty("test.type"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param fileType
	 *            File Type for the file that needs to be processed. It accepts two
	 *            (2) values: INPUT, OUTPUT
	 * @param inputFileName
	 *            File name of the file that needs to be processed.
	 * @return The file that was processed by the method
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static File getDataFile(String fileType, String inputFileName) throws CheetahException {
		logger.logMessage(null, "CheetahEngine", "Loading Properties file..", Constants.LOG_INFO, false);
		File file = null;
		FileUtils fileUtils = new FileUtils();
		file = fileUtils.getFile(fileType, inputFileName);
		logger.logMessage(null, "CheetahEngine", "Properties file loaded successfully.", Constants.LOG_INFO,
				false);
		return file;
	}

	/**
	 * @param appName
	 *            Name of the application for which the test is being executed.
	 */
	public static void processProperty(String appName) {
		// Dummy place holder - To be used later

	}

	/**
	 * @param e
	 *            Exception
	 * @return Exception Trace String
	 */
	public static String getExceptionTrace(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.ORIGINAL_EXCEPTION_STRING);
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append("\t\t");
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * @return WebDriver
	 */
	public static synchronized WebDriver getDriverInstance() {
		return driver;
	}

	/**
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private static void processVideoRecording() throws CheetahException {

		videoFileName = (CheetahEngine.props.getProperty("app.name") != null ? CheetahEngine.props.getProperty("app.name") : "APP") + "_"
				+ Calendar.getInstance().getTime().toString();
		videoFileName = videoFileName.replaceAll(" ", "_");
		videoFileName = videoFileName.replaceAll(":", "_");

		if ((CheetahEngine.props.getProperty("record.video") == null)
				|| (CheetahEngine.props.getProperty("record.video") != null && "FALSE".equalsIgnoreCase(CheetahEngine.props.getProperty("record.video")))) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Video Capture disabled",
					Constants.LOG_INFO, false);
		} else {
			if(checkSplitVideo()) {
				CheetahEngine.media.initiateVideo(reportingForm.getTransaction(), videoFileName);
			}else {
				// video recording for the entire suite
			}
		}
	}

	/**
	 * @param scenario
	 *            Scenario
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private static void processVideoCompletion(Scenario scenario) throws CheetahException {
		if ((CheetahEngine.props.getProperty("record.video") == null)
				|| (CheetahEngine.props.getProperty("record.video") != null && "FALSE".equalsIgnoreCase(CheetahEngine.props.getProperty("record.video")))) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Video Capture disabled",
					Constants.LOG_INFO, false);
		} else {
			
			if(checkSplitVideo()) {
				CheetahEngine.media.stopRecording();
				String finalName = scenario.getName() != null ? scenario.getName() : scenario.getId();
				updateVideoFileName(videoFileName, finalName);
				DBExecutor.recordVideo(reportingForm.getTransaction(), finalName);
			}else {
				// video recording managed for the entire suite
			}
		}

	}

	/**
	 * @param videoFileNameOrg
	 *            Original File Name
	 * @param finalName
	 *            Final File Name
	 * @throws CheetahException
	 */
	private static void updateVideoFileName(String videoFileNameOrg, String finalName) throws CheetahException {
		String filePath;
		String transaction = reportingForm.getTransaction();
		if (transaction != null && transaction.length() > 1) {
			filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO" + File.separator
					+ CheetahEngine.app_name + File.separator + transaction;
		} else {
			filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO" + File.separator
					+ CheetahEngine.app_name;
		}

		File originalFile = new File(filePath + File.separator + videoFileNameOrg + ".avi");
		File finalFile = new File(filePath + File.separator + finalName + ".avi");
		originalFile.renameTo(finalFile);
		CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(),
				"Renaming Video: " + videoFileNameOrg + " to " + finalName, Constants.LOG_INFO, false);
	}

	/**
	 * @return the handleStack
	 */
	public WindowHandleStack getHandleStack() {
		return handleStack;
	}

	/**
	 * @param handleStack
	 *            the handleStack to set
	 */
	public void setHandleStack(WindowHandleStack handleStack) {
		this.handleStack = handleStack;
	}

	/**
	 * Push Parent Window Handle to Stack
	 * 
	 * @param handle
	 *            Window Handle
	 */
	public void insertParentHandleToStack(String handle) {
		handleStack.push(handle);
	}

	/**
	 * Return Parent Window Handle
	 * 
	 * @return Parent Window Handle
	 */
	public String getParentHandleFromStack() {
		return handleStack.pop();
	}

}
