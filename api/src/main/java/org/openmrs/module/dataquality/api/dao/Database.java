/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality.api.dao;

/**
 *
 * @author lordmaul
 */

import java.sql.DriverManager;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsUtil;

/**
 * @author lordmaul
 */
public class Database {
	
	//static DBConnection openmrsConn = Utils.getNmrsConnectionDetails();
	
	public static Connection conn = null;
	
	//public static Connection [] connPool = new Connection[10];
	//public static DBConnection openmrsConn2 = null;
	
	public static ConnectionPool connectionPool;
	
	public static void initConnection() {
		try {
			System.out.println("initiing connection");
			/*Properties props;
			props = OpenmrsUtil.getRuntimeProperties("openmrs");
			String appDir = OpenmrsUtil.getApplicationDataDirectory();
			            Context.getAdministrationService().getSystemVariables();
			System.out.println("Appppppppppppppppplication direeeeeeeeeeeeeeeeetory: " + appDir);
			if (props == null) {
				props = OpenmrsUtil.getRuntimeProperties("openmrs-standalone");
				
			}*/
			
			String username = Context.getRuntimeProperties().getProperty("connection.username");//props.getProperty("connection.username");
			String password = Context.getRuntimeProperties().getProperty("connection.password");//props.getProperty("connection.password");
			String connectionUrl = Context.getRuntimeProperties().getProperty("connection.url");//props.getProperty("connection.url");
			
			connectionPool = new ConnectionPool("com.mysql.jdbc.Driver", connectionUrl, username, password, 1, 1, true);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void closeConnection() {
		if (connectionPool != null) {
			connectionPool.closeAllConnections();
		}
	}
	
	public static int setSQLMode(String sqlMode) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			String query = "SET GLOBAL sql_mode=?";
			int i = 1;
			stmt = con.prepareStatement(query);
			
			stmt.setString(i++, sqlMode);
			
			stmt.execute();
			
			return 1;
			
		}
		catch (SQLException ex) {
			Database.handleException(ex);
			return 0;
		}
		finally {
			Database.cleanUp(rs, stmt, con);
		}
	}
	
	/*public static void cleanUp(ResultSet rs, Statement stmt, Connection con) {
		try {
			Database.connectionPool.free(con);
			stmt.close();
			if (rs != null)
				rs.close();
			
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}*/
	
	public static void cleanUp(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null)
				rs.close();
			
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}
	
	public static void handleException(Exception ex) {
		ex.printStackTrace();
	}
}
