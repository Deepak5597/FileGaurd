package com.gehu.filegaurd.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/file_gaurd","root","root");  
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	} 
	
}
