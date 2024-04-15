package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import application.Controller;
import application.Controller2;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JDBCUtil {
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet result;
	private Controller c;
	
	public void setController(Controller controller) {
        this.c = controller;
    }
	
	public Connection connectDB() {
		 try {
			connect = DriverManager.getConnection("jdbc:mySQL://localhost:3306/sach", "root", "123456");
			 return connect;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean loginDB(ActionEvent e) throws IOException {
		connect = connectDB();
		
		try {
			String sql = "SELECT * FROM user WHERE username = ? and password = SHA2(?, 256)";
			statement = connect.prepareStatement(sql);
			statement.setString(1, c.getUsername().getText());
			statement.setString(2, c.getPassword().getText());
			result = statement.executeQuery();
			
			if(result.next()) {
				JOptionPane.showMessageDialog(null, "Successfully Login.",
						"Admin Message", JOptionPane.INFORMATION_MESSAGE);
				return true;
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.",
						"Admin Message", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean signupDB(ActionEvent e) {
		
		connect = connectDB();
		
		try {
			if(c.getSu_email().getText().isEmpty() || c.getSu_username().getText().isEmpty() || c.getSu_password().getText().isEmpty() || c.getSu_phone().getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"All fields are necessary to be filled", "Admin Message", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			else if(!c.getSu_password().getText().equals(c.getSu_reenter().getText())) {
				JOptionPane.showMessageDialog(null,
						"Password does not match", "Admin Message", JOptionPane.WARNING_MESSAGE);
				return false;
			}
				String sql = "INSERT INTO user(username, password, email, phoneNumber) VALUES(?,SHA2(?, 256),?, ?)";
				
				statement = connect.prepareStatement(sql);
				statement.setString(1, c.getSu_username().getText());
				statement.setString(2, c.getSu_password().getText());
				statement.setString(3, c.getSu_email().getText());
				statement.setString(4, c.getSu_phone().getText());
				statement.execute();
				return true;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
		
	}
}
