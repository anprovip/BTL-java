package controller;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.DAOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.ChangeScene;
import model.User;

public class LoginController implements Initializable{
	
    @FXML
    private BorderPane loginBorderPane;
    
	private DAOUser daoUser = DAOUser.getInstance();
	@FXML
    private Button loginButton;

    @FXML
    private PasswordField password;
    
    @FXML
    private TextField su_displayName;

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

    @FXML
    private Button chooseAvatar;
    
    @FXML
    private Label userID;
    
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

	public TextField getSu_displayName() {
		return su_displayName;
	}

	public void setSu_displayName(TextField su_displayName) {
		this.su_displayName = su_displayName;
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
	private User newUser = new User();
	private static LoginController instance;
    
    public static LoginController getInstance() {
        return instance;
    }
    @FXML
    private MyShelvesPageController myShelvesPageController;
    @FXML
    private UserController userController;
    
	@FXML
	public void onClickChooseAvatar(ActionEvent event) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Choose Avatar Image");
	    
	    // Set filter to only show image files
	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png");
	    fileChooser.getExtensionFilters().add(extFilter);
	    
	    // Show open file dialog
	    File selectedFile = fileChooser.showOpenDialog(null);
	    
	    if (selectedFile != null) {
	        // Get the absolute path of the selected file and set it as the image source for the new user
	        String imagePath = selectedFile.getAbsolutePath();
	        newUser.setImageSrc(imagePath);
	    }
	}

    public void login(ActionEvent e) throws IOException {
        if(daoUser.selectByUsernameAndPassword(username.getText(), password.getText())) {
        	JOptionPane.showMessageDialog(null, "Login successfully", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
		    new ChangeScene(loginBorderPane, "/views/HomePageScene.fxml");
		    if (myShelvesPageController != null) {
	            myShelvesPageController.reloadDataAndRefreshUI();
	        }
		    userController = UserController.getInstance();
            if (userController != null) {
                userController.reloadDataAndRefreshUI();
                userController.getUserInfo(username.getText());
            }
		} else {
		    JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.", "Admin Message", JOptionPane.ERROR_MESSAGE);
		}
    }

    public void signup(ActionEvent e) {
        
        if(su_email.getText().isEmpty() || su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_phone.getText().isEmpty() || su_displayName.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(null, "All fields are necessary to be filled", "Admin Message", JOptionPane.WARNING_MESSAGE);
        } else if(!su_password.getText().equals(su_reenter.getText())) {
            JOptionPane.showMessageDialog(null, "Password does not match", "Admin Message", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                boolean isUsernameTaken = daoUser.checkUsernameExist(su_username.getText());
                if(isUsernameTaken) {
                    JOptionPane.showMessageDialog(null, "This username is already taken.", "Admin Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    
                    newUser.setEmail(su_email.getText());
                    newUser.setPassword(su_password.getText());
                    newUser.setPhoneNumber(su_phone.getText());
                    newUser.setUsername(su_username.getText());
                    newUser
                    daoUser.insert(newUser);
                    JOptionPane.showMessageDialog(null, "Successfully Create new account", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @FXML
    void OnPressedEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
            	if(daoUser.selectByUsernameAndPassword(username.getText(), password.getText())) {
                	JOptionPane.showMessageDialog(null, "Login successfully", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
        		    new ChangeScene(loginBorderPane, "/views/HomePageScene.fxml");
        		    if (myShelvesPageController != null) {
        	            myShelvesPageController.reloadDataAndRefreshUI();
        	        }
        		    userController = UserController.getInstance();
                    if (userController != null) {
                        userController.reloadDataAndRefreshUI();
                        userController.getUserInfo(username.getText());
                    }
        		} else {
        		    JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.", "Admin Message", JOptionPane.ERROR_MESSAGE);
        		}
             }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void resetApp() {
        // Đặt lại trạng thái ban đầu của các trường nhập liệu và nhãn
        username.setText("");
        password.setText("");
        // Các dòng code khác để đặt lại trạng thái ban đầu của các thành phần giao diện khác ở đây (nếu cần)
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		myShelvesPageController = MyShelvesPageController.getInstance();
	}

}