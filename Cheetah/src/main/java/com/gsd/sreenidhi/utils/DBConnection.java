package com.gsd.sreenidhi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * Database connection for DB2 batabase - functionality
 * 
 * @author Sreenidhi, Gundlupet
 *
 */

public class DBConnection extends CheetahEngine {
	
	public static Connection conn = null;
	
	/**
	 * @return Connection
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static Connection db2connection() throws CheetahException {

		
		String jdbcClassName = CheetahEngine.configurator.dbConfigurator.getDriverClass();
		String url = CheetahEngine.configurator.dbConfigurator.getJdbcUrl();
		String uid = CheetahEngine.configurator.dbConfigurator.getUsername();
		String db2pwd = CheetahEngine.configurator.dbConfigurator.getPassword();

		try {
			Class.forName(jdbcClassName).newInstance();
			conn = DriverManager.getConnection(url, uid, db2pwd);
			System.out.println("**** Database Connection***-->"+conn);

		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
			throw new CheetahException(e);
		}  finally {
			if (conn != null) {
				CheetahEngine.logger.logMessage(null, "DBConnection",
						"Connection successful" ,
						Constants.LOG_INFO, false);
			}

		}
		return conn;
	}
	/**
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static void close_connection() throws CheetahException {
		try {
			conn.close();
	    } catch (SQLException e) {
	    	CheetahEngine.logger.logMessage(e, "DBConnection",
					"SQLException" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e),
					Constants.LOG_ERROR, true);
	    }
	}
}