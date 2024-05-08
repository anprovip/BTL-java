package controller;

import java.io.IOException;
import javax.swing.JOptionPane;

import database.DAOShelf;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Shelf;
import model.User;

public class ShelfCardToAddBooksController{
    @FXML
    private ImageView checkImage;

    @FXML
    private HBox shelfCardHBox;

    @FXML
    private Label shelfName;

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
