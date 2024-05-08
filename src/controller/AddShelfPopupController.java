package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import database.DAOShelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ChangeScene;
import model.Shelf;

public class AddShelfPopupController {
	@FXML
    private TextField shelfTextField;

    @FXML
    private Button addButton;
    
    private Stage stage;
    
    private Scene scene;
    
    @FXML
    private VBox addShelfBox;

    // Khởi tạo controller
    @FXML
    public void AddShelf(ActionEvent e) throws IOException {
    	String shelfName = shelfTextField.getText();
    	if(!shelfName.isEmpty()) {
    		Shelf shelf = new Shelf();
    		shelf.setShelfName(shelfName);
    		
    		DAOShelf.getInstance().insertShelf(shelf);
    		JOptionPane.showMessageDialog(null, "Add shelf successfully!");
    		addButton.setDisable(true);
    		
    		
    	}
    	else {
        	JOptionPane.showMessageDialog(null, "Please make sure you do not leave the shelf name blank!");
        }
    }
}
