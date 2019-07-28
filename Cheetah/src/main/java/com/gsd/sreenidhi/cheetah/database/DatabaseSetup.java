package com.gsd.sreenidhi.cheetah.database;

import java.util.LinkedHashMap;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DatabaseSetup {

	public LinkedHashMap<String, String> tables = new LinkedHashMap<String, String>();
	public LinkedHashMap<String, String> mysql_entries = new LinkedHashMap<String, String>();
	public LinkedHashMap<String, String> sqlServer_entries = new LinkedHashMap<String, String>();
	public LinkedHashMap<String, String> db2_entries = new LinkedHashMap<String, String>();
	public LinkedHashMap<String, String> oracle_entries = new LinkedHashMap<String, String>();
	

	
	protected static final String setDB = " use `"+CheetahEngine.configurator.dbConfigurator.getDatabaseName()+"`;";
	protected static final String ignoreForeignKeyConstraint = "SET FOREIGN_KEY_CHECKS = 0; ";
	protected static final String setForeignKeyConstraint = "SET FOREIGN_KEY_CHECKS = 1;";
	
	
	protected static final String mysql_transactions="CREATE TABLE TRANSACTIONS" + 
			"(" + 
			"ID_TRANSACTION int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"TX_TRANSACTION varchar(255)," +
			"TX_APPLICATION_NAME varchar(255) NOT NULL," + 
			"TX_START_TIME varchar(255)," + 
			"TX_END_TIME varchar(255)," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_TRANSACTION)" + 
			");";
	protected static final String mysql_scenarios="CREATE TABLE SCENARIOS" + 
			"(" + 
			"ID_SCENARIO int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"TX_SCENARIO_NAME varchar(500)," + 
			"TX_EXECUTION_STATUS varchar(255)," + 
			"TX_PAGE_URL varchar(255)," + 
			"TX_TEST_START_TIME varchar(255)," + 
			"TX_TEST_END_TIME varchar(255)," + 
			"TX_TEST_EXECUTION_TIME varchar(255)," + 
			"ID_SCENARIO_EXECUTION int," + 
			"ID_GHERKIN_SCENARIO varchar(255)," + 
			"TX_GHERKING_SCENARIO_STEPS LONGTEXT," + 
			"TX_GHERKIN_FEATURE varchar(255)," + 
			"TX_SCENARIO_EXECUTION_TAG LONGTEXT," + 
			"TX_SCENARIO_TAGS LONGTEXT," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_SCENARIO)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");";
	
	protected static final String mysql_screenshots="CREATE TABLE SCREENSHOTS" + 
			"(" + 
			"ID_SCREENSHOT int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"ID_SCENARIO int," + 
			"TX_SCREENSHOT_NAME varchar(500)," + 
			"DATA_SCREENSHOT LONGBLOB," + 
			"TX_EXECUTION_TIME varchar(255)," + 
			"TX_SCREENSHOT_TRIGGER varchar(255)," +
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_SCREENSHOT)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES SCENARIOS(ID_SCENARIO)" + 
			");";
	
	protected static final String mysql_videos="CREATE TABLE VIDEOS" + 
			"(" + 
			"ID_VIDEO int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"ID_SCENARIO int," + 
			"TX_VIDEO_NAME varchar(500)," + 
			"DATA_VIDEO LONGTEXT," + 
			"FL_SPLIT BOOLEAN," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_VIDEO)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");" ;
	
	protected static final String mysql_reports="CREATE TABLE REPORTS" + 
			"(" + 
			"ID_REPORT int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"TX_REPORT_TYPE varchar(100)," + 
			"TX_REPORT_NAME varchar(255)," + 
			"DATA_REPORT LONGBLOB," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_REPORT)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");";
	
	protected static final String mysql_errors="CREATE TABLE ERRORS" + 
			"(" + 
			"ID_ERROR int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"ID_SCENARIO int," + 
			"TX_ERROR_MESSAGE LONGTEXT," + 
			"TX_STACK_TRACE LONGTEXT," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_ERROR)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");";
	
	protected static final String mysql_logs="CREATE TABLE LOGS" + 
			"(" + 
			"ID_LOG int NOT NULL UNIQUE AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"ID_SCENARIO int," + 
			"TX_SOURCE varchar(255)," + 
			"TX_MESSAGE LONGTEXT," + 
			"FL_EMAIL BOOLEAN," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_LOG)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");" ;
	
	protected static final String mysql_transactionUsers="CREATE TABLE TRANSACTION_USER" + 
			"(" + 
			"ID_TRANSACTION_USER int NOT NULL AUTO_INCREMENT," + 
			"ID_TRANSACTION int NOT NULL," + 
			"TX_ACTIVE_DIRECTORY_UID VARCHAR(50)," + 
			"TX_USER_NAME VARCHAR(255)," + 
			"TS_CREATE Timestamp NOT NULL," + 
			"TS_UPDATE Timestamp," + 
			"PRIMARY KEY(ID_TRANSACTION_USER)," + 
			"FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)" + 
			");" ;
	
	protected static final String mysql_environment="CREATE TABLE ENVIRONMENT  " + 
			"			(  " + 
			"			ID_ENVIRONMENT int NOT NULL UNIQUE AUTO_INCREMENT,  " + 
			"			ID_TRANSACTION int NOT NULL,  " + 
			"			TX_CLOSE_BROWSER varchar(10),  " + 
			"			TX_EXECUTION_TAG LONGTEXT,  " + 
			"			TX_LOGGER LONGTEXT,  " + 
			"			TX_ENVIRONMENT varchar(10),  " + 
			"			TX_RECORD_VIDEO varchar(10)," + 
			"			TX_NETWORK_INFO LONGTEXT," + 
			"			TX_PROPERTIES LONGTEXT," + 
			"			TS_CREATE Timestamp NOT NULL,  " + 
			"			TS_UPDATE Timestamp,  " + 
			"			PRIMARY KEY(ID_ENVIRONMENT),  " + 
			"			FOREIGN KEY(ID_TRANSACTION) REFERENCES TRANSACTIONS(ID_TRANSACTION)  " + 
			"			);" ;


	 
	
	public DatabaseSetup() {
		tables.put("transactions", "TRANSACTIONS");
		tables.put("scenarios", "SCENARIOS");
		tables.put("screenshots", "SCREENSHOTS");
		tables.put("videos", "VIDEOS");
		tables.put("reports", "REPORTS");
		tables.put("errors", "ERRORS");
		tables.put("logs", "LOGS");
		tables.put("transactionuser", "TRANSACTION_USER");
		tables.put("environment", "ENVIRONMENT");
		
		
		mysql_entries.put("transactions", mysql_transactions);
		mysql_entries.put("scenarios", mysql_scenarios);
		mysql_entries.put("screenshots", mysql_screenshots);
		mysql_entries.put("videos", mysql_videos);
		mysql_entries.put("reports", mysql_reports);
		mysql_entries.put("errors", mysql_errors);
		mysql_entries.put("logs", mysql_logs);
		mysql_entries.put("transactionuser", mysql_transactionUsers);
		mysql_entries.put("environment", mysql_environment);
	}
}
