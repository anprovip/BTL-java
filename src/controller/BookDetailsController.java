package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import model.ChangeScene;
import model.Review;
import database.DAOBook;
import database.DAOReview;

public class BookDetailsController {
    
	@FXML
    private BorderPane DBookBorderPane;

    @FXML
    private Label authorName;

    @FXML
    private Button backButton;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private HBox home;

    @FXML
    private VBox homeBox;

    @FXML
    private HBox myShelves;

    @FXML
    private Button nextButton;

    @FXML
    private GridPane reviewContainer;

    @FXML
    private ScrollPane reviewScrollPane;

    @FXML
    private TextField reviewTextField;

    @FXML
    private HBox search;
    
    @FXML
    private Button addButton;
    
    

    public void setData(Book book) {
        System.out.println("Nhận được dữ liệu khi click: " + book.getBookID());
        book = DAOBook.getInstance().selectByID(book);
        if (book != null) {
            bookName.setText(book.getName());
            authorName.setText(book.getAuthor());
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
    

    @FXML
    private void onClickAddReview(MouseEvent event) {
        String reviewText = reviewTextField.getText();
        // Phải có ISBN và user ID để thêm review vào cơ sở dữ liệu
        String ISBN = ""; // Lấy ISBN từ đối tượng Book, tạm thời để là chuỗi rỗng
        int userID = 0; // Lấy user ID từ người dùng hoặc từ hệ thống, tạm thời để là 0
        int rating = 5; // Đánh giá mặc định, có thể thay đổi theo ý của người dùng

        if (!reviewText.isEmpty() && !ISBN.isEmpty() && userID != 0) {
            // Tạo đối tượng Review từ dữ liệu nhập vào
            Review review = new Review();
            review.setISBN(ISBN);
            review.setUserId(userID);
            review.setReviewText(reviewText);
            review.setRating(rating);

            DAOReview.getInstance().insert(review);


        } else {
            // Xử lý khi dữ liệu nhập vào không hợp lệ
        }
    }

    
    @FXML
    void onClickHome(MouseEvent event) throws IOException {
    	new ChangeScene(DBookBorderPane, "/views/HomePageScene.fxml");
    }

    @FXML
    void onClickSearch(MouseEvent event) throws IOException {
    	new ChangeScene(DBookBorderPane, "/views/SearchPageScene.fxml");
    }

    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	new ChangeScene(DBookBorderPane, "/views/UserScene.fxml");
    }
    
}
