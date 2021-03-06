package net.koreate.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectTest {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/smartWeb";
	String username= "smartWeb";
	String password = "12345";
	
	@org.junit.Test
	public void testConnection() {
		System.out.println("connection test");
		Connection conn = null;
		try {
			Class.forName(driver);
			System.out.println("driver 승인!");
			conn = DriverManager.getConnection(url,username,password);
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("class??");
		} catch (SQLException e) {
			System.out.println("sql??");
		}finally {
			try {
				if(conn != null)conn.close();
			} catch (SQLException e) {}
		}
	}
}
