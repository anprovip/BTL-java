package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.DAOBook;
import database.DAOReview;
import database.DAOShelf;
import database.DAOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.ChangeScene;
import model.User;

public class UserProfileController implements Initializable {
    @FXML
    private Button createButton;

    @FXML
    private HBox home;

    @FXML
    private ImageView imageUser;

    @FXML
    private HBox myShelves;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfFollowers;

    @FXML
    private Label numberOfFollowing;

    @FXML
    private Label numberOfShelves;

    @FXML
    private HBox search;

    @FXML
    private BorderPane UserProfileBorderPane;

    @FXML
    private HBox user;

    @FXML
    private Label userName;
    
    @FXML
    private MyShelvesPageController myShelvesPageController;
	
    private User currentUser;
    public void setData(User user) {
    	userName.setText(user.getUsername());
    	currentUser = DAOUser.getInstance().selectByUsername(user.getUsername());
        if (currentUser != null) {
            Blob imageBlob = currentUser.getImageUser();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imageUser.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            long userID = currentUser.getUserId();
            int shelfCount = DAOShelf.getInstance().countShelvesByUserID(userID);
            int bookCount = DAOShelf.getInstance().getTotalBooksByUserID(userID);
            numberOfShelves.setText(String.valueOf(shelfCount));
            numberOfBooks.setText(String.valueOf(bookCount));
        } else {
            // Xử lý khi không tìm thấy user
        }
        
    }
    
	@FXML
    void onClickHome(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/HomePageScene.fxml");
    	
    }

    @FXML
    void onClickSearch(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/SearchPageScene.fxml");
    }

    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/UserScene.fxml");
    }
    public void switchtoMyShelves(MouseEvent e) throws IOException {
		if(e.getSource() == myShelves) {
			new ChangeScene(UserProfileBorderPane, "/views/MyShelvesPageScene.fxml");
		}
	}
    @FXML
    public void onClickAdd(ActionEvent event) {
		try {
            // Tạo một Stage mới cho cửa sổ popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add your Shelf");

            // Load nội dung từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddShelfPopup.fxml"));
            VBox root = loader.load();

            // Gán controller cho cửa sổ popup
            //AddShelfPopupController controller = loader.getController();
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
            if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myShelvesPageController = MyShelvesPageController.getInstance();
		
	}
}
