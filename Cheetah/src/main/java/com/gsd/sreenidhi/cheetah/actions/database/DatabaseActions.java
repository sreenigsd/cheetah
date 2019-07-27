package com.gsd.sreenidhi.cheetah.actions.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DatabaseActions {

	protected static Connection connection = null;
	protected static PreparedStatement pstmt = null;
	protected static ResultSet rset = null;
	protected static ResultSetMetaData rsmd = null;

	private DatabaseActions() {
		// Constructor
	}

	/**
	 * @param dbClassName
	 *            Class name for the respective Databases Example:
	 *            com.ibm.db2.jcc.DB2Driver
	 * @param dbUrl
	 *            URL of the database to connect to
	 * @param loginID
	 *            Login Credentials - login ID
	 * @param password
	 *            Login Credentials - password
	 * @return Connection
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static Connection connect_to_db(String dbClassName, String dbUrl, String loginID, String password)
			throws CheetahException {
		String jdbcClassName = dbClassName;
		String url = dbUrl;
		String uid = loginID;
		String dbPwd = password;

		try {
			Class.forName(jdbcClassName).newInstance();
			// Establish connection
			connection = DriverManager.getConnection(url, uid, dbPwd);

		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
			throw new CheetahException(e);
		} catch (InstantiationException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"InstantiationException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
			throw new CheetahException(e);
		} catch (IllegalAccessException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"IllegalAccessException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
			throw new CheetahException(e);
		} catch (ClassNotFoundException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"ClassNotFoundException: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
			throw new CheetahException(e);
		} finally {
			if (connection != null) {
				CheetahEngine.logger.logMessage(null, "DatabaseActions", "Connection Successful!", Constants.LOG_INFO,
						false);
			}
		}
		return connection;

	}

	/**
	 * Execute SQL against database
	 * 
	 * @param sql
	 *            The SQL statement that needs to be executed.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 * @return ResultSet
	 */
	public static ResultSet execute_sql(String sql) throws CheetahException {
		try {
			CheetahEngine.logger.logMessage(null, "DatabaseActions", "query: " + sql, Constants.LOG_ERROR, false);
			pstmt = connection.prepareStatement(sql);
			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return rset;
	}

	/**
	 * Execute SQL against database
	 * 
	 * @param sql
	 *            The SQL statement that needs to be executed.
	 * @return status
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static int executeUpdate_sql(String sql) throws CheetahException {
		int rs = 0;
		try {
			CheetahEngine.logger.logMessage(null, "DatabaseActions", "query: " + sql, Constants.LOG_INFO, false);
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}

		return rs;
	}

	/**
	 * Print the database results
	 * 
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void print_result() throws CheetahException {

		try {
			rsmd = rset.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rset.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rset.getString(i);
					CheetahEngine.logger.logMessage(null,"", rsmd.getColumnName(i) + " = " + columnValue, Constants.LOG_INFO);
				//	System.out.print(rsmd.getColumnName(i) + " = " + columnValue);
				}
				CheetahEngine.logger.logMessage(null,"", "", Constants.LOG_INFO);
			}
		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}

	}

	/**
	 * retrieve the database results
	 * 
	 * @return the database result
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static String get_result() throws CheetahException {
		String columnValue = null;
		try {
			rsmd = rset.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rset.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					columnValue = rset.getString(i);
					System.out.print(rsmd.getColumnName(i) + " = " + columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return columnValue;

	}

	/**
	 * @return ResultSet
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static ResultSet get_resultSet() throws CheetahException {
		return rset;
	}

	/**
	 * Close Database Connection
	 * 
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void close_connection() throws CheetahException {
		try {
			if (rset != null)
				rset.close();

			if (pstmt != null)
				pstmt.close();

			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			CheetahEngine.logger.logMessage(e, "DatabaseActions",
					"SQL Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
	}

}
