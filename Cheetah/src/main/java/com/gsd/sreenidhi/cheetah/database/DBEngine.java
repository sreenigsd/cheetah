package com.gsd.sreenidhi.cheetah.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DBEngine {
	public Connection connect_to_db(String URL, String User, String pw) {
		Connection con = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName(CheetahEngine.configurator.dbConfigurator.getDriverClass()).newInstance();
			con = DriverManager.getConnection(URL, User, pw);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
	
	
	public ResultSet executeSQL(Connection conn, String SQL) throws SQLException {
		Statement stmt=conn.createStatement();  
		ResultSet rs=stmt.executeQuery(SQL);  
		return rs;
	}
	
	public int executeUpdateSQL(Connection conn, String SQL) throws SQLException {
		Statement stmt=conn.createStatement();  
		int rs=stmt.executeUpdate(SQL);  
		return rs;
	}
	
}
