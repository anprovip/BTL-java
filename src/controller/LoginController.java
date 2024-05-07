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
    
    private static User newUser = new User();
    
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
	    else{
	    	String defaultImagePath = "/img/banana.png"; // Thay đổi đường dẫn tới ảnh mặc định tại đây
	    	newUser.setImageSrc(defaultImagePath);
	    }
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
                    
                    newUser.setEmail(su_email.getText());
                    newUser.setPassword(su_password.getText());
                    newUser.setPhoneNumber(su_phone.getText());
                    newUser.setUsername(su_username.getText());
                    
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
 
        		} else {
        		    JOptionPane.showMessageDialog(null, "Wrong username or password. Please enter again.", "Admin Message", JOptionPane.ERROR_MESSAGE);
        		}
             }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}