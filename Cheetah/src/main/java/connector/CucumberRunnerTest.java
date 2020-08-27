package connector;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.gsd.sreenidhi.automation.config.Configurator;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.actions.Cognator;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.database.DBInitializer;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.engine.CheetahForm;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.cheetah.reporting.Media;
import com.gsd.sreenidhi.cheetah.reporting.ReportingBean;
import com.gsd.sreenidhi.cheetah.reporting.ReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.ReportingForm;
import com.gsd.sreenidhi.cheetah.reporting.csv.CSVReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.html.HTMLReportingEngine;
import com.gsd.sreenidhi.cheetah.reporting.pdf.PDFReportingEngine;
import com.gsd.sreenidhi.cheetah.runner.ExtendedCucumberRunner;
import com.gsd.sreenidhi.utils.CalendarUtils;
import com.gsd.sreenidhi.utils.CheetahUtils;
import com.gsd.sreenidhi.utils.FileUtils;
import com.gsd.sreenidhi.utils.NetworkUtils;
import com.gsd.sreenidhi.utils.SystemEnvironment;
import com.gsd.sreenidhi.utils.ZipUtils;

import io.cucumber.testng.AbstractTestNGCucumberTests;

//import org.junit.runner.RunWith;
//import io.cucumber.junit.CucumberOptions;
//import org.junit.Rule;
//import org.junit.runner.RunWith;

import io.cucumber.testng.CucumberOptions;
//@RunWith(Cucumber.class)
/**
 * @author Sreenidhi, Gundlupet
 *
 */


//@RunWith(ExtendedCucumberRunner.class)
@CucumberOptions(strict = true,
features =  {"feature-link"} , 
	glue =  {"glue-link"} , 
	plugin = { "json:target/cucumber.json",
				"html:target/cucumber-html-report", 
				"pretty:target/cucumber-pretty.txt", 
				"usage:target/cucumber-usage.json",
				"junit:target/cucumber-junit-results.xml", 
				"testng:target/cucumber-testng-tesults.xml",
				"rerun:src/test/resources/rerun.txt" }
	,tags=""
		)
public class CucumberRunnerTest  extends AbstractTestNGCucumberTests{
//public class CucumberRunnerTest {

	/**
	 * This is the Test binding object. All tests are initiated and completed within
	 * this object. All pre- and post- hooks for Cucumber scenarios are handled
	 * here.
	 */
	public CucumberRunnerTest() {

	}

	/**
	 * Set retry count argument
	 */
	//@Rule
	public RetryRule retryRule = new RetryRule(3);

	/**
	 * This method is the pre-hook for a test execution. This method initializes the
	 * base and all required components of the test
	 * 
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	@BeforeSuite
	public static void setUp() throws CheetahException{

		CheetahUtils CheetahUtils = new CheetahUtils();
		CheetahEngine.app_name = "CHEETAH";
		CheetahEngine.logger = new Log(CheetahEngine.app_name);
		CheetahEngine.media = new Media();

		CheetahEngine.cheetahForm.setStartTime(Calendar.getInstance().getTime());
		CheetahEngine.cheetahForm.setExecutionTimestamp(CalendarUtils.getCurrentTimeStamp());
		String transactionID = CheetahUtils.generateTransaction();
		Date transactionTime = Calendar.getInstance().getTime();

		CheetahEngine.configurator = new Configurator();
		CheetahEngine.configurator.loadConfigurators();

		try {
			DBInitializer.dbInitialize();
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
			throw new CheetahException(e);
		}

		CheetahEngine.props = CheetahEngine.initializeProperties();
		CheetahEngine.app_name = CheetahEngine.props.getProperty("app.name");
		CheetahEngine.logger.logMessage(null, "CucumberRunnerTest", "Suite Start", Constants.LOG_INFO, false);

		FileUtils fu = new FileUtils();
		fu.createTestDataFile();

		CheetahEngine.reportingForm = new ReportingForm();
		CheetahEngine.reportingBeanList = CheetahEngine.reportingForm.getReportingBean();
		if (CheetahEngine.reportingBeanList == null) {
			CheetahEngine.reportingBeanList = new ArrayList<ReportingBean>();
		}
		CheetahEngine.reportingForm.setTransaction(transactionID);
		CheetahEngine.reportingForm.setStatusPass(0);
		CheetahEngine.reportingForm.setStatusFail(0);
		CheetahEngine.reportingForm.setTimeStamp(transactionTime);
		CheetahEngine.logger.logMessage(null, "CucumberRunnerTest", "Transaction ID: " + transactionID,
				Constants.LOG_INFO, false);
		CheetahEngine.logger.logMessage(null, "CucumberRunnerTest",
				"Transaction Timestamp: " + transactionTime.toString(), Constants.LOG_INFO, false);

		DBExecutor.recordNewTransaction(transactionID, CheetahEngine.app_name, transactionTime.toString());

		CheetahUtils.createNewNode();

		if ((CheetahEngine.props.getProperty("record.video") == null)
				|| (CheetahEngine.props.getProperty("record.video") != null
						&& CheetahEngine.props.getProperty("record.video").equalsIgnoreCase("FALSE"))) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Video Capture disabled",
					Constants.LOG_INFO, false);
		} else {

			if (CheetahEngine.checkSplitVideo()) {
				// video recording by test case
			} else {
				CheetahEngine.media.initiateVideo(null, transactionID);
			}
		}

		// Invokation of beforeSuiteMethod
		getBeforeMethod(new World() {
			public boolean getBeforeMethod() {
				return true;
			}
		});
		
		CheetahEngine.report = new ExtentReports();
		//CheetahEngine.report.setAnalysisStrategy(AnalysisStrategy.BDD);
		CheetahEngine.report.setSystemInfo("Execution Tag: ", CheetahEngine.props.getProperty("execution.tag")!=null?CheetahEngine.props.getProperty("execution.tag"):"");
		try {
			CheetahEngine.report.setSystemInfo("Host Name: ", NetworkUtils.getHostName());
		} catch (UnknownHostException e) {
			throw new CheetahException(e); 
		}
		CheetahEngine.report.setSystemInfo("LAN Addr: ", NetworkUtils.getLocalHostLANAddress().toString());
		CheetahEngine.report.setSystemInfo("User: ", System.getProperty("user.name"));
		
		
		
		CheetahEngine.htmlSparkReporter = new ExtentSparkReporter("./target/cheetah-test-automation-report.html");
		CheetahEngine.htmlSparkReporter.config().setDocumentTitle("Cheetah Test Automation");
		CheetahEngine.htmlSparkReporter.config().setReportName(CheetahEngine.props.getProperty("app.name"));
		
		CheetahEngine.htmlReporter = new ExtentHtmlReporter("./target/test-report.html");
		CheetahEngine.htmlReporter.config().setDocumentTitle("Cheetah Test Automation");
		
		//CheetahEngine.report.attachReporter(CheetahEngine.htmlReporter);
		CheetahEngine.report.attachReporter(CheetahEngine.htmlSparkReporter);
		
		
	}

	/**
	 * This method is the post-hook of a test. All components generated for the test
	 * are destroyed in this method.
	 * 
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	@AfterSuite
	public static void tearDown() throws CheetahException{
		CheetahEngine.cheetahForm.setEndTime(Calendar.getInstance().getTime());
		processReports();
		if (CheetahEngine.props.getProperty("close.browser") != null
				&& CheetahEngine.props.getProperty("close.browser").equalsIgnoreCase("FALSE")) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Browser remains Open",
					Constants.LOG_INFO, false);
		} else {
			CheetahEngine.logger.logMessage(null, CucumberRunnerTest.class.getName(), "Close Driver Attempt",
					Constants.LOG_INFO, false);
			// CheetahEngine.getDriverInstance().close();

			if (CheetahEngine.getDriverInstance() != null) {
				try {
					CheetahEngine.logger.logMessage(null, CucumberRunnerTest.class.getName(),
							"Driver: " + CheetahEngine.getDriverInstance().toString(), Constants.LOG_INFO, false);
					CheetahEngine.logger.logMessage(null, CucumberRunnerTest.class.getName(), "Quit Driver Attempt",
							Constants.LOG_INFO, false);
					CheetahEngine.getDriverInstance().quit();
				} catch (Exception e) {
					CheetahEngine.logger.logMessage(e, CucumberRunnerTest.class.getName(),
							"Quit Driver Attempt Exception: " + CheetahEngine.getExceptionTrace(e), Constants.LOG_INFO,
							false);
				}

			}
		}

		if ((CheetahEngine.props.getProperty("record.video") == null)
				|| (CheetahEngine.props.getProperty("record.video") != null
						&& CheetahEngine.props.getProperty("record.video").equalsIgnoreCase("FALSE"))) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Video Capture disabled",
					Constants.LOG_INFO, false);
		} else {
			if (CheetahEngine.checkSplitVideo()) {
				// video recording by test case
			} else {
				CheetahEngine.media.stopRecording();
				DBExecutor.recordVideo(null, CheetahEngine.cheetahForm.getTransactionID());
			}
		}

		CheetahUtils CheetahUtils = new CheetahUtils();

		CheetahUtils.destroyNode();

		CheetahUtils.processNotification(CheetahEngine.props.getProperty("email.file.name"), true);
		CheetahEngine.logger.logMessage(null, "CucumberRunnerTest", "Suite Stop", Constants.LOG_INFO, false);

		prepareVideoReport();

		// Invokation of afterSuiteMethod
		getAfterMethod(new World() {
			public boolean getAfterMethod() {
				return true;
			}
		});

		// Update Database if DB enabled
		if (DBInitializer.validDB) {
			Date transactionTime = Calendar.getInstance().getTime();
			SystemEnvironment.processEnvironment(CheetahEngine.props);
			DBExecutor.updateTransaction(transactionTime.toString(),
					CheetahEngine.cheetahForm.getDbId() != null ? CheetahEngine.cheetahForm.getDbId().toString() : "");
		}
		
		CheetahEngine.report.flush();
	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private static void prepareVideoReport() throws CheetahException {

		String filePath;
		String transaction = CheetahEngine.reportingForm.getTransaction();

		if ((CheetahEngine.props.getProperty("record.video") == null)
				|| (CheetahEngine.props.getProperty("record.video") != null
						&& CheetahEngine.props.getProperty("record.video").equalsIgnoreCase("FALSE"))) {
			CheetahEngine.logger.logMessage(null, CheetahEngine.class.getName(), "Video Capture disabled",
					Constants.LOG_INFO, false);
		} else {
			filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO" + File.separator
					+ CheetahEngine.app_name + File.separator + transaction;

			if (CheetahEngine.props.getProperty("target.archive.video") == null
					|| (CheetahEngine.props.getProperty("target.archive.video") != null
							&& "TRUE".equalsIgnoreCase(CheetahEngine.props.getProperty("target.archive.video")))) {
				if (CheetahEngine.checkSplitVideo()) {
					ZipUtils.archiveDirectory(filePath);
				} else {
					filePath = filePath + ".avi";
					ZipUtils.archiveFile(filePath);
				}
			}

		}
	}

	/**
	 * **********************************************************************************
	 * This section is used to identify the beforeSuiteMethod and afterSuiteMethod
	 * calls
	 * ***********************************************************************************
	 */

	interface World {
		// boolean getAfterMethod();
	}

	private static final Method getAfterMethod;
	static {
		Method m = null;
		try {
			if (isClass(CheetahEngine.props.getProperty("glue").concat(".Runner"))) {
				m = Class.forName(CheetahEngine.props.getProperty("glue").concat(".Runner"))
						.getMethod("afterSuiteMethod");
			}

		} catch (Exception e) {
		}
		getAfterMethod = m;
	}

	/* Call this method instead from your code. */
	public static boolean getAfterMethod(World world) {
		if (getAfterMethod != null) {
			try {
				return ((Boolean) getAfterMethod.invoke(world)).booleanValue();
			} catch (Exception e) {
				// doesn't matter
			}
		}
		return false;
	}

	private static final Method getBeforeMethod;
	static {
		Method m = null;
		try {
			if (isClass(CheetahEngine.props.getProperty("glue").concat(".Runner"))) {
				m = Class.forName(CheetahEngine.props.getProperty("glue").concat(".Runner"))
						.getMethod("beforeSuiteMethod");
			}

		} catch (Exception e) {
		}
		getBeforeMethod = m;
	}

	/* Call this method instead from your code. */
	public static boolean getBeforeMethod(World world) {
		if (getBeforeMethod != null) {
			try {
				return ((Boolean) getBeforeMethod.invoke(world)).booleanValue();
			} catch (Exception e) {
				// doesn't matter
			}
		}
		return false;
	}

	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/*
	 * *****************************************************************************
	 * ***** End of Method identification section
	 * *****************************************************************************
	 * ******
	 */

	/**
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private static void processReports() throws CheetahException {
		ReportingEngine reportingEngine = new CSVReportingEngine();
		reportingEngine.generateReport();
		reportingEngine.recordSuiteResults();
		reportingEngine.consolidateReport();
		reportingEngine.recordHistory();

		ReportingEngine reportingEnginePDF = new PDFReportingEngine();
		reportingEnginePDF.generateReport();

		ReportingEngine reportingEnginehtml = new HTMLReportingEngine();
		reportingEnginehtml.generateReport();
		reportingEnginehtml.consolidateReport();
	}

	
}