package controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import model.Book;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BookController {
	  @FXML
	  private Label authorName;

	  @FXML
	  private BorderPane bookBorderPane;

	  @FXML
	  private ImageView bookImage;

	  @FXML
	  private Label bookName;
	  
	  @FXML
	  private Label bookID;
	  
	  private Stage stage;
	  private Scene scene;
    
    public void setData(Book book) {
        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        bookID.setText(book.getBookID());
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
    } 
    @FXML
    void onClickImage(MouseEvent event) throws IOException {
    	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookDetailsScene.fxml"));
        Parent root = loader.load();
        BookDetailsController controller = loader.getController();
        Book book = new Book();
        book.setBookID(bookID.getText());
        controller.setData(book);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }


     @FXML
     void onClickName(MouseEvent event) throws IOException {
    	 //new ChangeScene(bookBorderPane, "/views/BookDetailsScene.fxml");
    	 Parent root = FXMLLoader.load(getClass().getResource("/views/BookDetailsScene.fxml"));
    	 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	 scene = new Scene(root, 1440, 900);
    	 stage.setScene(scene);
    	 stage.show();
     }
        
    
}