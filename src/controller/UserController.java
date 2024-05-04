package controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.DAOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ChangeScene;
import model.User;

public class UserController implements Initializable{
	
	private Stage stage;
	
    @FXML
    private VBox addBookBox;

    @FXML
    private Button addCover;

    @FXML
    private HBox backBox;

    @FXML
    private TextField bookTitle;

    @FXML
    private ImageView coverImage;

    @FXML
    private TextField displayName;

    @FXML
    private Button editButton;

    @FXML
    private TextField emailInfo;

    @FXML
    private ImageView imageInfo;

    @FXML
    private TextField isbn;

    @FXML
    private ListView<String> listView;

    @FXML
    private HBox logoutBox;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField oldPassword;

    @FXML
    private HBox password;

    @FXML
    private VBox passwordBox;

    @FXML
    private TextField phoneInfo;

    @FXML
    private HBox profile;

    @FXML
    private VBox profileBox;

    @FXML
    private TextField pubYear;

    @FXML
    private TextField reenterPasswordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveButton1;

    @FXML
    private TextArea summary;

    @FXML
    private BorderPane userBorderPane;

    private LoginController loginController;
    
    private static UserController instance;
    
    public static UserController getInstance() {
        return instance;
    }
    
    DAOUser daoUser = DAOUser.getInstance();
    User user = User.getInstance();
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    public void switchToHome(MouseEvent e) throws IOException {
    	if(e.getSource() == backBox) {
    		new ChangeScene(userBorderPane, "/views/HomePageScene.fxml");
    		
    	}
    }
    public void switchToLogin(MouseEvent e) throws IOException {
    	if(e.getSource() == logoutBox) {
    		
    		int choice = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
    		if(choice == JOptionPane.YES_OPTION) {
    			loginController = LoginController.getInstance();
    			loginController.resetApp();
    			User.getInstance().clearUserData();
    			if (myShelvesPageController != null) {
                    myShelvesPageController.reloadDataAndRefreshUI();
                }
    			
    			new ChangeScene(userBorderPane, "/views/LoginScene.fxml");
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
                
                // Lấy đường dẫn tuyệt đối của file
                String absolutePath = file.getAbsolutePath();
                
                user.setImageSrc(absolutePath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu không thể chuyển đổi đường dẫn thành URL
            }
        }
    }

    
    public void getUserInfo(String currentUsername) {
        User user = daoUser.selectByUsername(currentUsername);

        if (user != null) {
            emailInfo.setText(user.getEmail());
            phoneInfo.setText(user.getPhoneNumber());
            displayName.setText(user.getDisplayName());
            Blob imageBlob = user.getImageUser();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imageInfo.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            
            System.out.println("User not found!");
        }
    }
    
    @FXML
    public void saveUserInfo(ActionEvent event) {
        // Lấy thông tin từ các TextField
        String dName = displayName.getText();
        String email = emailInfo.getText();
        String phoneNumber = phoneInfo.getText();
        String absolutePath = user.getImageSrc();
            // Tạo đối tượng User mới với thông tin được điền mới
            User updatedUser = new User();
            updatedUser.setDisplayName(dName);
            updatedUser.setEmail(email);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setUsername(user.getUsername());
            updatedUser.setImageSrc(absolutePath);
            // Gọi phương thức updateUserInfo từ DAOUser để cập nhật thông tin trong cơ sở dữ liệu
            boolean success = daoUser.updateUserInfo(updatedUser);
            
            if (success) {
                // Thông báo cho người dùng rằng thông tin đã được cập nhật thành công
                JOptionPane.showMessageDialog(null, "User information updated successfully!");
            } else {
                // Thông báo lỗi nếu không thể cập nhật thông tin
                JOptionPane.showMessageDialog(null, "Failed to update user information!");
            }
        }
    
    @FXML
    private void savePassword(ActionEvent event) {
        // Lấy thông tin từ các TextField
        String oldPass = oldPassword.getText();
        if(!oldPass.equals(user.getPassword())) {
        	JOptionPane.showMessageDialog(null, "Your old password is wrong!");
            return;
        }
        String newPassword = newPasswordField.getText(); // Sử dụng newPasswordField thay vì newPassword
        String reenteredPassword = reenterPasswordField.getText(); // Sử dụng reenterPasswordField thay vì reenterPassword
        
        // Kiểm tra xem mật khẩu mới và mật khẩu nhập lại có khớp nhau không
        if (!newPassword.equals(reenteredPassword)) {
            // Thông báo lỗi nếu mật khẩu nhập lại không trùng khớp với mật khẩu mới
            JOptionPane.showMessageDialog(null, "New password and re-entered password do not match!");
            return; // Kết thúc phương thức nếu có lỗi
        }
        
        // Gọi hàm changePassword từ DAOUser để thay đổi mật khẩu
        boolean success = daoUser.changePassword(user.getUsername(), newPassword);
        
        if(success) {
        	JOptionPane.showMessageDialog(null, "Update password successfully!");
        }
    }


    @FXML
    private void switchToProfile(MouseEvent event) {
        profileBox.setVisible(true);
        passwordBox.setVisible(false);
        addBookBox.setVisible(false);
    	
    }

    @FXML
    private void switchToPassword(MouseEvent event) {
        profileBox.setVisible(false);
        passwordBox.setVisible(true);
        addBookBox.setVisible(false);
    }
    
    @FXML
    void switchToAddBook(MouseEvent event) {
    	profileBox.setVisible(false);
        passwordBox.setVisible(false);
        addBookBox.setVisible(true);
    }
    
    public void reloadDataAndRefreshUI() {
        // Lấy lại thông tin người dùng từ cơ sở dữ liệu
    	
        String currentUsername = user.getUsername();
        getUserInfo(currentUsername);
        
        // Cập nhật thông tin người dùng trên giao diện
        emailInfo.setText(user.getEmail());
        phoneInfo.setText(user.getPhoneNumber());
        oldPassword.setText("");
        newPasswordField.setText("");
        reenterPasswordField.setText("");
        System.out.println("User data reloaded and UI refreshed.");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginController = LoginController.getInstance();
		String currentUsername = User.getInstance().getUsername();
		getUserInfo(currentUsername);
		//System.out.println(user.toString());
		myShelvesPageController = MyShelvesPageController.getInstance();
		instance = this;
		
	}

}
