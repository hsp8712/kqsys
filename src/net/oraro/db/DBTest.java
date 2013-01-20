package net.oraro.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kqsys", "root", "root");
			
			System.out.println(conn.getMetaData().getDriverName());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
}
