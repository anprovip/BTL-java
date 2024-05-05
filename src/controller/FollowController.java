package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DAOFollow;
import database.DAOShelf;
import database.DAOUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Book;
import model.Follow;
import model.Shelf;
import model.User;

public class FollowController {
    @FXML
    private Label displayNameInFollow;

    @FXML
    private HBox followCardHbox;

    @FXML
    private ImageView userInFollowImage;

    @FXML
    private Label usernameInFollow;
    
    private Stage stage;  
    private Scene scene;
    
    public void setDataForFollower(Follow follow) {
    	User user = DAOUser.getInstance().selectByFollowerID((long)follow.getFollowerId());
        displayNameInFollow.setText(user.getDisplayName());
        usernameInFollow.setText(user.getUsername());
        Blob imageBlob = user.getImageUser();
        if (imageBlob != null) {
            try {
                // Chuyển đổi Blob thành mảng byte
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                Image image = new Image(new ByteArrayInputStream(imageData));
                userInFollowImage.setImage(image);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
    public void setDataForFollowing(Follow follow) {
    	User user = DAOUser.getInstance().selectByFollowingID((long)follow.getFollowingId());
        displayNameInFollow.setText(user.getDisplayName());
        usernameInFollow.setText(user.getUsername());
        Blob imageBlob = user.getImageUser();
        if (imageBlob != null) {
            try {
                // Chuyển đổi Blob thành mảng byte
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                Image image = new Image(new ByteArrayInputStream(imageData));
                userInFollowImage.setImage(image);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    } 
    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserProfileScene.fxml"));
        Parent root = loader.load();
        UserProfileController controller = loader.getController();
        User user = new User();
        user.setUsername(usernameInFollow.getText());
        controller.setData(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }
}
