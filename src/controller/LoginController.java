package controller;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.JDBCUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Connection connect;
	private PreparedStatement statement, check;
	private ResultSet result;
	private JDBCUtil jdbcUtil = new JDBCUtil();
	@FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    @FXML
    private Button su_button;

    @FXML
    private TextField su_email;

    @FXML
    private TextField su_password;

    @FXML
    private TextField su_phone;

    @FXML
    private TextField su_reenter;

    @FXML
    private TextField su_username;

	
	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public Button getSu_button() {
		return su_button;
	}

	public void setSu_button(Button su_button) {
		this.su_button = su_button;
	}

	public TextField getSu_email() {
		return su_email;
	}

	public void setSu_email(TextField su_email) {
		this.su_email = su_email;
	}

	public TextField getSu_password() {
		return su_password;
	}

	public void setSu_password(TextField su_password) {
		this.su_password = su_password;
	}

	public TextField getSu_phone() {
		return su_phone;
	}

	public void setSu_phone(TextField su_phone) {
		this.su_phone = su_phone;
	}

	public TextField getSu_reenter() {
		return su_reenter;
	}

	public void setSu_reenter(TextField su_reenter) {
		this.su_reenter = su_reenter;
	}

	public TextField getSu_username() {
		return su_username;
	}

	public void setSu_username(TextField su_username) {
		this.su_username = su_username;
	}
	
	
	public void login(ActionEvent e) throws IOException {
		connect = jdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM user WHERE username = ? and password = SHA2(?, 256)";
			statement = connect.prepareStatement(sql);
			statement.setString(1, username.getText());
			statement.setString(2, password.getText());
			result = statement.executeQuery();
			
			if(result.next()) {
				
				String username = result.getString("username");
                String password = result.getString("password");
                String email = result.getString("email");
                String phoneNumber = result.getString("phoneNumber");
                User user = new User(username, password, email, phoneNumber);
				JOptionPane.showMessageDialog(null, "Successfully Login.",
						"Admin Message", JOptionPane.INFORMATION_MESSAGE);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomePageScene.fxml"));
				root = loader.load();
				HomePageController hpc = loader.getController();
				
				stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				scene = new Scene(root, 1515, 770);
				stage.setTitle("Goodreads");
				stage.setScene(scene);
				stage.show();
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.",
						"Admin Message", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void signup(ActionEvent e) {
		
		connect = jdbcUtil.getConnection();
		
		
			if(su_email.getText().isEmpty() || su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_phone.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"All fields are necessary to be filled", "Admin Message", JOptionPane.WARNING_MESSAGE);
				
			}
			else if(!su_password.getText().equals(su_reenter.getText())) {
				JOptionPane.showMessageDialog(null,
						"Password does not match", "Admin Message", JOptionPane.WARNING_MESSAGE);
				
			}
			else {
				try {
					String checkUserName = "SELECT * FROM user WHERE username = ?";
					check = connect.prepareStatement(checkUserName);
					check.setString(1, su_username.getText());
					result = check.executeQuery();
					if(result.next()) {
						 String username = result.getString("username");
			             String password = result.getString("password");
			             String email = result.getString("email");
			             String phoneNumber = result.getString("phoneNumber");
			             User user = new User(username, password, email, phoneNumber);
			             
						JOptionPane.showMessageDialog(null,
								"This username is already taken.", "Admin Message", JOptionPane.WARNING_MESSAGE);
						
					}
					else {
					String sql = "INSERT INTO user(username, password, email, phoneNumber) VALUES(?,SHA2(?, 256),?, ?)";
					
					statement = connect.prepareStatement(sql);
					statement.setString(1, su_username.getText());
					statement.setString(2, su_password.getText());
					statement.setString(3, su_email.getText());
					statement.setString(4, su_phone.getText());
					statement.execute();
					JOptionPane.showMessageDialog(null,
							"Successfully Create new account", "Admin Message", JOptionPane.INFORMATION_MESSAGE);

					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		}
}
