
package application;

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

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Connection connect;
	private PreparedStatement statement, check;
	private ResultSet result;
	
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
	
	private JDBCUtil jdbcUtil = new JDBCUtil();
	public boolean loginDB(ActionEvent e) throws IOException {
		connect = jdbcUtil.connectDB();
		
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
	/*
	public void signup(ActionEvent e) throws IOException {
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.setController(this, null);
		if(jdbcUtil.signupDB(e)) {
			JOptionPane.showMessageDialog(null,
					"Successfully Create new account", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
	
	}
	*/
}
