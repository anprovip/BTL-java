package controller;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DAOUser;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ChangeScene;
import model.User;

public class LoginController {
	
    @FXML
    private BorderPane loginBorderPane;
    
	private DAOUser daoUser = DAOUser.getInstance();
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
        if(daoUser.selectByUsernameAndPassword(username.getText(), password.getText())) {
        	JOptionPane.showMessageDialog(null, "Login successfully", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
		    new ChangeScene(loginBorderPane, "/views/HomePageScene.fxml");
		} else {
		    JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.", "Admin Message", JOptionPane.ERROR_MESSAGE);
		}
    }

    public void signup(ActionEvent e) {
        
        if(su_email.getText().isEmpty() || su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_phone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are necessary to be filled", "Admin Message", JOptionPane.WARNING_MESSAGE);
        } else if(!su_password.getText().equals(su_reenter.getText())) {
            JOptionPane.showMessageDialog(null, "Password does not match", "Admin Message", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                boolean isUsernameTaken = daoUser.checkUsernameExist(su_username.getText());
                if(isUsernameTaken) {
                    JOptionPane.showMessageDialog(null, "This username is already taken.", "Admin Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    User newUser = new User(su_username.getText(), su_password.getText(), su_email.getText(), su_phone.getText());
                    daoUser.insert(newUser);
                    JOptionPane.showMessageDialog(null, "Successfully Create new account", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
            }
        }
    }
}