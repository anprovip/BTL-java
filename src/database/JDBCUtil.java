package database;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class JDBCUtil {
	private Connection connect;
	private PreparedStatement statement, check;
	private ResultSet result;
	
	
	
	public Connection connectDB() {
		 try {
			connect = DriverManager.getConnection("jdbc:mySQL://localhost:3306/sach", "root", "An123456789@");
			 return connect;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public void getUserInfo() {
		this.user = user;

        try {
            connect = connectDB();
            String sql = "SELECT * FROM user WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, currentUsername); 
            result = statement.executeQuery();

            if (result.next()) {
                c3.getUsernameInfo().setText((result.getString("username")));
                c3.getPasswordInfo().setText((result.getString("password")));
                c3.getEmailInfo().setText((result.getString("email")));
                c3.getPhoneInfo().setText((result.getString("phoneNumber")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
    }
	/*
	public boolean saveUserInfo(User user) {
        connect = connectDB();
        
        try {
            String sql = "UPDATE user SET username=?, password=SHA2(?, 256), email=?, phoneNumber=? WHERE username=?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getUsername());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User info updated successfully!");
                return true;
            } else {
                System.out.println("Failed to update user info!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    */
	}

