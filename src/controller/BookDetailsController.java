package controller;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Book;
import database.DAOBook;

import java.net.URL;
import java.util.ResourceBundle;

public class BookDetailsController implements Initializable {
    
    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    private String bookID;

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Book book = new Book();
        book.setBookID(bookID);
        setData(book);
    }

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
}
