package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DAOShelf;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

public class BookInShelfController {

    @FXML
    private Label authorName;

    @FXML
    private Label averageRating;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private ImageView deleteButton;

    @FXML
    private Label shelfNameDetail;
    
    private Book currentBook;
    
    public static Book currentBookInShelf;
    
    private Stage stage;
    
    private Scene scene;
    

    
    public void setData(Book book) {
        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        averageRating.setText(Float.toString(book.getAverageRating()));
        Blob imageBlob = book.getImageBook();
        shelfNameDetail.setText(book.getShelfName());
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
        currentBook = book;
        currentBookInShelf = book;
    }
    
    @FXML
    public void onClickImage(MouseEvent event) throws IOException {
        if (event.getSource() == deleteButton) {
            if (currentBook != null) {
            	int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this book?", "Delete this book.", JOptionPane.YES_NO_OPTION);
            	if(dialogResult == JOptionPane.YES_OPTION) {
	                boolean deleted = DAOShelf.getInstance().deleteBookFromShelf(currentBook);
	                if (deleted) {
	                    JOptionPane.showMessageDialog(null, "Delete successfully!");
	                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MyShelvesPageScene.fxml"));
	                    Parent root = loader.load();
	                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                    scene = new Scene(root, 1440, 900);
	                    stage.setScene(scene);
	                    stage.show();
	                } else {
	                	JOptionPane.showMessageDialog(null, "Can't delete!");
	                }
            	}
            } else {
                // Xử lý khi không có cuốn sách hiện tại
            }
        }
    }


    

}
