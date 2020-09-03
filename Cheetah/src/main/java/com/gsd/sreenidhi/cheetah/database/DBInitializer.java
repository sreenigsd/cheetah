package com.gsd.sreenidhi.cheetah.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DBInitializer {

	protected static DatabaseSetup dbSetup;
	protected static Connection connection;
	public static boolean validDB = false;
	public static boolean dbCreated = false;
	public static boolean dbTablesValidated = false;

	public static boolean finalDBValidation = false;

	public static void validateDBConnection() throws CheetahException, SQLException {

		if (CheetahEngine.configurator.dbConfigurator.isUseDB()) {

			try {
				DBEngine dbe = new DBEngine();
				connection = dbe.connect_to_db(CheetahEngine.configurator.dbConfigurator.getJdbcUrl(),
						CheetahEngine.configurator.dbConfigurator.getUsername(),
						CheetahEngine.configurator.dbConfigurator.getPassword());

				if (connection != null) {
					validDB = true;
				} else {
					validDB = false;
					Exception e = new SQLException();
					CheetahEngine.logger.logMessage(e, "DBInitializer", "Database validation failed !",
							Constants.LOG_INFO, true);
				}
			}catch(Exception e)
			{
				throw new CheetahException(e);
			}
		} else {
			CheetahEngine.logger.logMessage(null, "DBInitializer", "Database usage Disabled... :( ", Constants.LOG_INFO,
					false);
		}

	}

	public static void initNewDB() throws CheetahException {
		int dbTables = 0;
		if (validDB) {
			DBEngine dbe = new DBEngine();
			dbSetup = new DatabaseSetup();

			Iterator it = null;

			if (CheetahEngine.configurator.dbConfigurator.getDatabaseType() != null) {
				if ("mysql".equalsIgnoreCase(CheetahEngine.configurator.dbConfigurator.getDatabaseType())) {
					it = dbSetup.mysql_entries.entrySet().iterator();
				}
			} else {
				Exception e = new Exception("Unidentified Database Type");
				throw new CheetahException(e);
			}

			if (it != null) {
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					it.remove(); // avoids a ConcurrentModificationException

					int res;
					try {
						res = dbe.executeUpdateSQL(connection, pair.getValue().toString());
						if (res >= 0) {
							dbTables++;
						}
					} catch (SQLException e) {
						throw new CheetahException(e);
					}

				}
			} else {
				Exception e = new Exception("Failed to Create Database Connections");
				throw new CheetahException(e);
			}
		}
		if (dbTables == (dbSetup.tables.size())) {
			dbCreated = true;
		}
	}

	public static void checkDBMetaData(Connection connection)
			throws SQLException, IllegalArgumentException, IllegalAccessException {
		int dbTables = 0;
		dbSetup = new DatabaseSetup();
		DatabaseMetaData dbm = connection.getMetaData();
	
		ResultSet tables;
		Iterator it = null;
		if (validDB) {
			it = dbSetup.tables.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
	
				tables = dbm.getTables(null, null, pair.getValue().toString(), null);
				if (tables.next()) {
					// Table exists
					dbTables++;
				} else {
					// Table does not exist
				}

			}
		}

		if (dbTables == (dbSetup.tables.size())) {
			dbTablesValidated = true;
		}

	}

	private static void purgeTables() throws IllegalArgumentException, IllegalAccessException, SQLException {
		Field[] fields = null;
		if (validDB) {
			DBEngine dbe = new DBEngine();
			dbSetup = new DatabaseSetup();

			int existing_tables = 0;
			ResultSet rs = dbe.executeSQL(connection,
					"select count(*) from information_schema.tables where table_schema = '"+CheetahEngine.configurator.dbConfigurator.getDatabaseName()+"';");
			while (rs.next()) {
				existing_tables = rs.getInt(1);
			}

			if (existing_tables > 0) {
				dbe.executeSQL(connection, dbSetup.setDB);
				dbe.executeSQL(connection, dbSetup.ignoreForeignKeyConstraint);

				Iterator it = dbSetup.tables.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					it.remove(); // avoids a ConcurrentModificationException

					ResultSet res1 = dbe.executeSQL(connection, "SHOW TABLES LIKE '" + pair.getValue() + "';");
					while (res1.next()) {
						int res = dbe.executeUpdateSQL(connection, "DROP TABLE " + pair.getValue() + ";");
						if (res > 0) {
							// dbTables++;
						}
					}
				}
				dbe.executeSQL(connection, dbSetup.setForeignKeyConstraint);
			}

		}

	}

	public static void dbInitialize()
			throws IllegalArgumentException, IllegalAccessException, CheetahException, SQLException {

		CheetahEngine.logger.logMessage(null, DBInitializer.class.getName(), "Initialize DB Connections",
				Constants.LOG_INFO, false);

		DBEngine dbe = new DBEngine();
		validateDBConnection();
		if (validDB && connection != null) {
			checkDBMetaData(connection);
			if (!dbTablesValidated) {
				// Tables not loaded
				CheetahEngine.logger.logMessage(null, DBInitializer.class.getName(), "Recreating DB Schema...",
						Constants.LOG_INFO, false);
				purgeTables();

				initNewDB();
				if (dbCreated) {
					checkDBMetaData(connection);
					if (dbTablesValidated) {
						CheetahEngine.logger.logMessage(null, DBInitializer.class.getName(), "DB Creation Successful!",
								Constants.LOG_INFO, false);
						// System.out.println("DB Creation successful");
					} else {
						// Something went wrong
					}
				}
			} else {
				// DB Already exists - tables validated
				CheetahEngine.logger.logMessage(null, DBInitializer.class.getName(), "DB Validation Complete!",
						Constants.LOG_INFO, false);
			}
		}
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String args[])
			throws IllegalArgumentException, IllegalAccessException, CheetahException, SQLException {
		System.out.println("starting");
		DBEngine dbe = new DBEngine();
		validateDBConnection();
		if (validDB && connection != null) {
			checkDBMetaData(connection);
			if (!dbTablesValidated) {
				// Tables not loaded
				purgeTables();

				initNewDB();
				if (dbCreated) {
					checkDBMetaData(connection);
					if (dbTablesValidated) {
						System.out.println("DB Creation successful");
					} else {
						// Something went wrong
					}
				}
			} else {
				// DB Already exists - tables validated
			}

		}
		if (connection != null) {
			connection.close();
		}
	}

}
