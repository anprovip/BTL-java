package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import database.DAOBook;
import database.DAOReview;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.ChangeScene;

public class UserProfileController {

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
    private BorderPane searchPageBorderPane;

    @FXML
    private HBox user;
    
    @FXML
    private Label userName;

    @FXML
    void onClickAdd(ActionEvent event) {

    }

    @FXML
    public void switchBox(MouseEvent event) throws IOException {
        new ChangeScene(searchPageBorderPane, "/views/HomePageScene.fxml");
    }

    @FXML
    void switchtoMyShelves(MouseEvent event) throws IOException {
    	new ChangeScene(searchPageBorderPane, "/views/MyShelvesPageScene.fxml");
    }

    @FXML
    void switchtoUserInformation(MouseEvent event) throws IOException {
    	new ChangeScene(searchPageBorderPane, "/views/UserScene.fxml");
    }
    
    public void setData() {
    	//nextButton.setDisable(false);
    	BookID.setText(book.getBookID());
        System.out.println("Nhận được dữ liệu khi click: " + book.getBookID());
        currentBook = book;
        book = DAOBook.getInstance().selectByID(book);
        recentlyAdded = DAOReview.getInstance().selectByCondition(book.getBookID());
        System.out.println(recentlyAdded.size());
        allReviews.addAll(recentlyAdded); 
        if(allReviews.size()<=itemsPerPage) nextButton.setDisable(true);
	    backButton.setDisable(true);
        showReviews(0, itemsPerPage);
        if (book != null) {
            bookName.setText(book.getName());
            authorName.setText(book.getAuthor());
            averageRating.setText(Float.toString(book.getAverageRating()));
            Blob imageBlob = book.getImageBook();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    bookImage.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Xử lý khi không tìm thấy sách
        }
    }

}
