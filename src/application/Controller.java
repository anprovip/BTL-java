
package application;

import java.io.IOException;

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

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

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
		JDBCUtil jdbcUtil = new JDBCUtil();
		
		jdbcUtil.setController(this);
		
		if(jdbcUtil.loginDB(e)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
			root = loader.load();
			Controller2 c2 = loader.getController();
			
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root, 1515, 770);
			stage.setTitle("Goodreads");
			stage.setScene(scene);
			stage.show();
		}
	}
	public void signup(ActionEvent e) throws IOException {
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.setController(this);
		if(jdbcUtil.signupDB(e)) {
			JOptionPane.showMessageDialog(null,
					"Successfully Create new account", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
	
	}
}
