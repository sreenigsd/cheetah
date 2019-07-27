package com.gsd.sreenidhi.cheetah.database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.ReportingBean;

import cucumber.api.Scenario;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DBExecutor {

	/**
	 * @param transaction
	 *            Transaction ID
	 * @param App_Name
	 *            Application Name
	 * @param transactionStartTime
	 *            Time of transaction start
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordNewTransaction(String transaction, String App_Name, String transactionStartTime)
			throws CheetahException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO TRANSACTIONS(TX_TRANSACTION, TX_APPLICATION_NAME, TX_START_TIME, TX_END_TIME, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			try {
				DBEngine dbe = new DBEngine();
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, transaction);
				pstmt.setString(2, App_Name);
				pstmt.setString(3, transactionStartTime);
				pstmt.setString(4, "");
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						CheetahEngine.cheetahForm.setDbId(generatedKeys.getLong(1));
						CheetahEngine.logger.logMessage(null, DBExecutor.class.getName(),
								"Primary Key: " + generatedKeys.getLong(1), Constants.LOG_INFO, false);
					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

		}

	}

	/**
	 * @param endTime
	 *            Transaction end time
	 * @param transactionId
	 *            Transaction ID
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void updateTransaction(String endTime, String transactionId) throws CheetahException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		CheetahEngine.logger.logMessage(null, DBExecutor.class.getName(), "Attempting DB Transaction Update", Constants.LOG_INFO);
		String transaction_SQL = "UPDATE TRANSACTIONS " + " SET TX_END_TIME = ?, " + " TS_UPDATE = ? "
				+ " WHERE ID_TRANSACTION = ? ;";

		
		if (DBInitializer.validDB) {
			DBEngine dbe = new DBEngine();
			try {
				CheetahEngine.logger.logMessage(null, DBExecutor.class.getName(), "Updating Transaction in DB", Constants.LOG_INFO);
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, endTime);
				pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(3, transactionId);

				rset = pstmt.executeUpdate();

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new CheetahException(e);
			}

		}
	}

	/**
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordNewScenario() throws CheetahException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO SCENARIOS(ID_TRANSACTION, TS_CREATE, TS_UPDATE) " + "VALUES(?,?,?);";

		if (DBInitializer.validDB) {
			try {
				DBEngine dbe = new DBEngine();
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						CheetahEngine.reportingForm.setDbScenarioID(generatedKeys.getLong(1));
					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

		}
	}

	/**
	 * @param scenario
	 *            Scenario
	 * @param bean
	 *            ReportingBean
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void updateScenario(Scenario scenario, ReportingBean bean) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "DBExecutor - Update Scenario",
				"Updating Scenario: " + DBInitializer.validDB, Constants.LOG_INFO, false);
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "UPDATE SCENARIOS " + "SET  TX_SCENARIO_NAME = ? , " + "TX_EXECUTION_STATUS = ? , "
				+ "TX_PAGE_URL = ? , " + "TX_TEST_START_TIME = ? , " + "TX_TEST_END_TIME = ? , "
				+ "TX_TEST_EXECUTION_TIME = ? , " + "ID_SCENARIO_EXECUTION = ? , " + "ID_GHERKIN_SCENARIO = ? , "
				+ "TX_GHERKING_SCENARIO_STEPS = ? , " + "TX_GHERKIN_FEATURE = ? , " + "TX_SCENARIO_EXECUTION_TAG = ? , "
				+ "TX_SCENARIO_TAGS = ? , " + "TS_UPDATE = ?  " + "WHERE ID_TRANSACTION = ? " + "AND ID_SCENARIO = ? ;";

		if (DBInitializer.validDB) {
			DBEngine dbe = new DBEngine();
			try {

				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL);
				pstmt.setString(1, scenario.getName());
				pstmt.setString(2, scenario.getStatus());
				pstmt.setString(3, bean.getTest_Page_URL());
				pstmt.setString(4, bean.getTestStartTime().toString());
				pstmt.setString(5, bean.getTestEndTime().toString());
				pstmt.setString(6, String.valueOf(bean.getExecutionTime()));
				pstmt.setString(7, bean.getScenarioExecutionId());
				pstmt.setString(8, scenario.getId());
				pstmt.setString(9, ""); // steps
				pstmt.setString(10, ""); // feature file
				pstmt.setString(11, CheetahEngine.props.getProperty("execution.tag"));
				pstmt.setString(12, scenario.getSourceTagNames().toString());
				pstmt.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(14, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(15, CheetahEngine.reportingForm.getDbScenarioID().toString());

				rset = pstmt.executeUpdate();

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				throw new CheetahException(e);
			}
		}

		CheetahEngine.logger.logMessage(null, "DBExecutor - Update Scenario", "Complete", Constants.LOG_INFO, false);

	}

	/**
	 * @param data
	 *            Byte Data
	 * @param time
	 *            Time of Screenshot
	 * @param trigger
	 *            Screenshot Trigger
	 * @param screenshotName
	 *            Screenshot name
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordScreenshot(byte[] data, String time, String trigger, String screenshotName)
			throws CheetahException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO SCREENSHOTS(ID_TRANSACTION, ID_SCENARIO, TX_SCREENSHOT_NAME, DATA_SCREENSHOT, TX_EXECUTION_TIME, TX_SCREENSHOT_TRIGGER, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			try {
				DBEngine dbe = new DBEngine();
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(2, CheetahEngine.reportingForm.getDbScenarioID().toString());
				pstmt.setString(3, screenshotName);
				pstmt.setBytes(4, data);
				pstmt.setString(5, time);
				pstmt.setString(6, trigger);
				pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {

					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new CheetahException(e);
			}

		}

	}

	/**
	 * @param transaction
	 *            Transactino ID
	 * @param fileName
	 *            File Name
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordVideo(String transaction, String fileName) throws CheetahException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO VIDEOS(ID_TRANSACTION, ID_SCENARIO, TX_VIDEO_NAME, DATA_VIDEO, FL_SPLIT, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			try {
				String filePath;
				if (transaction != null && transaction.length() > 1) {
					filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO"
							+ File.separator + CheetahEngine.app_name + File.separator + transaction;
				} else {
					filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO"
							+ File.separator + CheetahEngine.app_name;
				}

				String fileAddress = filePath + File.separator + fileName + ".avi";
				/*
				 * File file = new File(fileAddress); FileInputStream fileInputStream = new
				 * FileInputStream(file); ByteArrayOutputStream out = new
				 * ByteArrayOutputStream(); while (fileInputStream.available() > 0) {
				 * out.write(fileInputStream.read()); } byte[] videoByteArray =
				 * out.toByteArray();
				 */

				DBEngine dbe = new DBEngine();
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(2, CheetahEngine.reportingForm.getDbScenarioID().toString());
				pstmt.setString(3, fileName);
				pstmt.setString(4, com.gsd.sreenidhi.utils.NetworkUtils.getLocalHostLANAddress() + "||" + fileAddress);

				if (CheetahEngine.checkSplitVideo()) {
					pstmt.setBoolean(5, true);
				} else {
					pstmt.setBoolean(5, false);
				}
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {

					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new CheetahException(e);
			}
		}
	}

	/**
	 * @param report_type
	 *            Report Type
	 * @param report_name
	 *            Report Name
	 * @param report_path
	 *            Report Path
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordReport(String report_type, String report_name, String report_path)
			throws CheetahException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO REPORTS(ID_TRANSACTION, TX_REPORT_TYPE, TX_REPORT_NAME, DATA_REPORT, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?, ?, ?, ?, ?, ?);";

		if (DBInitializer.validDB) {
			DBEngine dbe = new DBEngine();
			
			FileInputStream fileInputStream = null;
			try {

				File file = new File(report_path);
				fileInputStream = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				while (fileInputStream.available() > 0) {
					out.write(fileInputStream.read());
				}
				byte[] reportByteArray = out.toByteArray();

				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(2, report_type);
				pstmt.setString(3, report_name);
				pstmt.setBytes(4, reportByteArray);
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {

					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException | IOException e) {
				throw new CheetahException(e);
			}finally {
				try {
					if(fileInputStream!=null)
					fileInputStream.close();
				} catch (IOException e) {
					throw new CheetahException(e);
				}
			}
		}
	}

	/**
	 * @param msg
	 *            Error Message
	 * @param trace
	 *            Stack Trace
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordError(String msg, String trace) throws CheetahException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO ERRORS(ID_TRANSACTION, ID_SCENARIO, TX_ERROR_MESSAGE, TX_STACK_TRACE, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			DBEngine dbe = new DBEngine();
			try {
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(2, CheetahEngine.reportingForm.getDbScenarioID().toString());
				pstmt.setString(3, msg);
				pstmt.setString(4, trace);
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				CheetahEngine.logger.logMessage(null, "DBExecutor - Errors", "Stmt: " + pstmt, Constants.LOG_INFO, false);
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {

					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				throw new CheetahException(e);
			}

		}
	}

	/**
	 * @param source
	 *            Source of Log
	 * @param message
	 *            Log Message
	 * @param fl_email
	 *            Boolean indicating whether this log message triggered an email
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordLog(String source, String message, boolean fl_email) throws CheetahException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO LOGS(ID_TRANSACTION, ID_SCENARIO, TX_SOURCE, TX_MESSAGE, FL_EMAIL, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			if (CheetahEngine.cheetahForm != null && CheetahEngine.cheetahForm.getDbId() != null
					&& CheetahEngine.reportingForm != null && CheetahEngine.reportingForm.getDbScenarioID() != null) {
				DBEngine dbe = new DBEngine();
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
					pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
					pstmt.setString(2, CheetahEngine.reportingForm.getDbScenarioID().toString());
					pstmt.setString(3, source);
					pstmt.setString(4, message);
					pstmt.setBoolean(5, fl_email);
					pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
					pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
					rset = pstmt.executeUpdate();

					try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {

						} else {
							throw new SQLException("Creating record failed, no ID obtained.");
						}
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {
					throw new CheetahException(e);
				}
			}

		}

	}

	/**
	 * @param properties
	 *            String of Properties
	 * @param inetAddr
	 *            Network Address
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void recordEnvironment(String properties, InetAddress inetAddr) throws CheetahException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rset = 0;

		String transaction_SQL = "INSERT INTO ENVIRONMENT(ID_TRANSACTION, TX_CLOSE_BROWSER, TX_EXECUTION_TAG, TX_LOGGER, TX_ENVIRONMENT, TX_RECORD_VIDEO, TX_NETWORK_INFO, TX_PROPERTIES, TS_CREATE, TS_UPDATE) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?);";

		if (DBInitializer.validDB) {
			DBEngine dbe = new DBEngine();
			try {
				connection = getConnection();
				pstmt = connection.prepareStatement(transaction_SQL, pstmt.RETURN_GENERATED_KEYS);
				pstmt.setString(1, CheetahEngine.cheetahForm.getDbId().toString());
				pstmt.setString(2,
						(CheetahEngine.props.getProperty("close.browser") != null ? CheetahEngine.props.getProperty("close.browser") : "DEFAULT - TRUE"));
				pstmt.setString(3, (CheetahEngine.props.getProperty("execution.tag") != null ? CheetahEngine.props.getProperty("execution.tag") : ""));
				pstmt.setString(4,
						(CheetahEngine.props.getProperty("log.location")!=null ? CheetahEngine.props.getProperty("log.location") + File.separator + "Reports"
								: "DEFAULT - " + new File("." + File.separator + "target" + File.separator + "Reports")
										.getAbsolutePath()));
				pstmt.setString(5,CheetahEngine.configurator.executionConfigurator.getExecutionEnv());
				pstmt.setString(6,
						(CheetahEngine.props.getProperty("record.video") != null ? CheetahEngine.props.getProperty("record.video") : "DEFAULT - FALSE"));
				pstmt.setString(7, inetAddr!=null?inetAddr.toString():"Could not be identified.");
				pstmt.setString(8, properties);
				pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
				
				rset = pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {

					} else {
						throw new SQLException("Creating record failed, no ID obtained.");
					}
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				throw new CheetahException(e);
			}
		}else {
		//	CheetahEngine.logger.logMessage(null, SystemEnvironment.class.getName(), "DB Disabled" , Constants.LOG_INFO);
		}
	}
	
	
	/**
	 * @return Connection
	 */
	public static Connection getConnection() {
		DBEngine dbe = new DBEngine();
		Connection conn = 	dbe.connect_to_db(CheetahEngine.configurator.dbConfigurator.getJdbcUrl(), CheetahEngine.configurator.dbConfigurator.getUsername(),
				CheetahEngine.configurator.dbConfigurator.getPassword());
		return conn;
		
	}
}
