package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import database.JDBCUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;

public class Controller3 {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private HBox backBox;

    @FXML
    private Button editButton;

    @FXML
    private TextField emailInfo;

    @FXML
    private HBox logoutBox;

    @FXML
    private TextField passwordInfo;

    @FXML
    private TextField phoneInfo;

    @FXML
    private VBox profileBox;

    @FXML
    private Button saveButton;

    @FXML
    private TextField usernameInfo;
    
    @FXML
    private ImageView imageInfo;
    
    private Controller c;
    
    private User currentUser;
    private JDBCUtil jdbcUtil = new JDBCUtil();

    
    public void switchToHome(MouseEvent e) throws IOException {
    	if(e.getSource() == backBox) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
    		root = loader.load();
    		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }
    public void switchToLogin(MouseEvent e) throws IOException {
    	if(e.getSource() == logoutBox) {
    		int choice = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
    		if(choice == JOptionPane.YES_OPTION) {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
	    		root = loader.load();
	    		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    		scene = new Scene(root, 1515, 770);
	    		stage.setScene(scene);
	    		stage.show();
    		}
    	}
    }
    @FXML
    private void editImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        
        // Set filter cho file chooser để chỉ chấp nhận các file ảnh
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Mở hộp thoại chọn tập tin
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                // Chuyển đổi đường dẫn file thành URL
                String localUrl = file.toURI().toURL().toString();
                
                // Tạo ảnh mới từ URL và cập nhật imageInfo
                Image image = new Image(localUrl);
                imageInfo.setImage(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu không thể chuyển đổi đường dẫn thành URL
            }
        }
    }
/*
    private void displayUserInfo() {
        // Hiển thị thông tin của người dùng lên giao diện
        usernameInfo.setText(currentUser.getUsername());
        passwordInfo.setText(currentUser.getPassword());
        emailInfo.setText(currentUser.getEmail());
        phoneInfo.setText(currentUser.getPhoneNumber());
        // Có thể hiển thị thông tin khác nếu cần
    }
    @FXML
    public void saveUserInfo(ActionEvent event) {
        currentUser.setPassword(passwordInfo.getText());
        currentUser.setEmail(emailInfo.getText());
        currentUser.setPhoneNumber(phoneInfo.getText());
        jdbcUtil.saveUserInfo(currentUser);
    }
    */
}

