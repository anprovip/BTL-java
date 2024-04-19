package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Book;
import model.ChangeScene;
import database.DAOBook;

public class BookDetailsController {
    
    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private BorderPane DBookBorderPane;




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
