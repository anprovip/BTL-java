package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCUtil {
	
	public static Connection getConnection() {
		Connection connection = null; 
		try {
			connection = DriverManager.getConnection("jdbc:mySQL://localhost:3306/sach", "root", "An123456789@");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static void closeConnection(Connection connection) {
		try {
			if(connection!=null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	}

