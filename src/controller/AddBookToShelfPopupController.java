package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOShelf;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Shelf;
import model.User;

public class AddBookToShelfPopupController implements Initializable{
    @FXML
    private ScrollPane addBookToShelfScrollPane;

    @FXML
    private GridPane listShelfContainer;
    
    @FXML
    
    private List<Shelf> allShelfUserHave = new ArrayList<>();
    private List<Shelf> recentlyUserAdded;

    private List<Shelf> getAllShelvesFromDatabase(User user) {
		return DAOShelf.getInstance().selectByCondition(Long.toString(user.getUserId()));
		
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		User user = User.getInstance();
		System.out.println(user.getUserId());
		recentlyUserAdded = new ArrayList<>(getAllShelvesFromDatabase(user));
		allShelfUserHave.addAll(recentlyUserAdded);
	    
		showShelves();
	}
    
    public void showShelves() {
    	
        System.out.println("Co den day khong");
        int column = 0;
        int row = 1;
        for (int i = 0; i < allShelfUserHave.size(); i++) {
            Shelf shelf = allShelfUserHave.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShelfCardToAddBook.fxml"));
            
            try {
            	HBox shelfPane = loader.load();
            	ShelfCardToAddBooksController shelfCardToAddBooksController = loader.getController();
            	shelfCardToAddBooksController.setData(shelf);
            	listShelfContainer.add(shelfPane, column, row++);
                GridPane.setMargin(shelfPane, new Insets(10));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
