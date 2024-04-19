package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Book;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Random;

public class CardController {
    @FXML
    private Label authorName;

    @FXML
    private Label bookName;

    @FXML
    private HBox box;

    @FXML
    private ImageView bookImage;
    
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};
    
    private Random random = new Random();

    public void setData(Book book) {
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
        
        if (book.getName() != null) {
            bookName.setText(book.getName());
        }
        
        if (book.getAuthor() != null) {
            authorName.setText(book.getAuthor());
        }

        String randomColor = colors[random.nextInt(colors.length)];
        box.setStyle("-fx-background-color: #" + randomColor + ";" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10);");
    }
}
