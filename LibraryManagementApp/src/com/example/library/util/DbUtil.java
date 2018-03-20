package com.example.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class to provide us with the Connection object
 * @author Devesh Shetty
 *
 */
public class DbUtil {

	public static Connection getConnection(String dbUrl, String username, String pass) 
			throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(dbUrl, username, pass);
	}
	
}
