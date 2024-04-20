package controller;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Book;
import model.Review;

public class ReviewController {

    @FXML
    private Label rating;

    @FXML
    private Label reviewDate;

    @FXML
    private Label reviewText;

    @FXML
    private Label userId;

    @FXML
    private ImageView userImage;

    @FXML
    private Label username;
    
    public void setData(Review review) {
        reviewText.setText(review.getReviewText());
        rating.setText(String.valueOf(review.getRating())); // Chuyển đổi rating sang String
        userId.setText(String.valueOf(review.getUserId())); // Chuyển đổi userId sang String
        reviewDate.setText(review.getReviewDate().toString()); // Chuyển đổi reviewDate sang String
        userId.setVisible(false);
        username.setText(review.getUsername());
        Blob imageBlob = review.getUserImage(); // Lấy Blob chứa hình ảnh người dùng từ đối tượng Review
        if (imageBlob != null) {
            try {
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                Image image = new Image(new ByteArrayInputStream(imageData));
                userImage.setImage(image);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
