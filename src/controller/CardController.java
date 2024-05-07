package controller;

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

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    private Label rateOfBook;
    
    private Stage stage;
	private Scene scene;
    
	@FXML
    private ImageView bookImage;
	
	public Book currentBook;
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
        float averageRatingValue = book.getAverageRating();
        String formattedRating = String.format("%.1f", averageRatingValue);
        rateOfBook.setText(formattedRating);
        
        if (book.getName() != null) {
            bookName.setText(book.getName());
        }
        
        if (book.getAuthor() != null) {
            authorName.setText(book.getAuthor());
        }
        currentBook = book;
        String randomColor = colors[random.nextInt(colors.length)];
        box.setStyle("-fx-background-color: #" + randomColor + ";" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10);");
    }
    @FXML
    public void onClickCard(MouseEvent event) throws IOException {
    	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookDetailsScene.fxml"));
        Parent root = loader.load();
        BookDetailsController controller = loader.getController();
        Book book = new Book();
        book.setBookID(currentBook.getBookID());
        controller.setData(book);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }
}
