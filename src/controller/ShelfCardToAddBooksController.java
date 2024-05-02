package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.DAOBook;
import database.DAOShelf;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.Shelf;
import model.User;

public class ShelfCardToAddBooksController{
    @FXML
    private ImageView checkImage;

    @FXML
    private HBox shelfCardHBox;

    @FXML
    private Label shelfName;
    
    private Stage stage;
	private Scene scene;
	private String bookID;
	
    
    public void setData(Shelf shelf) {
        shelfName.setText(shelf.getShelfName());
        bookID = BookDetailsController.currentBook.getBookID();
        System.out.println(bookID);
        
    } 
    @FXML
    void onClickShelf(MouseEvent event) throws IOException {
        String savedShelfName = shelfName.getText();

        long userId = User.getInstance().getUserId();

        Shelf shelf = new Shelf();
        shelf.setShelfName(savedShelfName);
        shelf.setUserID(userId);
        shelf.setBookID(bookID);

        // Kiểm tra xem sách đã tồn tại trong shelf chưa
        if (DAOShelf.getInstance().isBookInShelf(shelf)) {
            System.out.println("The book already exists in this shelf.");
            JOptionPane.showMessageDialog(null, "The book already exists in this shelf.");
        } else {
            // Nếu sách chưa tồn tại, thêm nó vào shelf và đặt checkImage thành visible
            DAOShelf.getInstance().insert(shelf);
            checkImage.setVisible(true);
        }
    }

	
}
