package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DAOShelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.Shelf;
import model.User;

public class ShelfController {
    @FXML
    private Label shelfName;
    
    private Stage stage;  
    private Scene scene;

    @FXML
    private ImageView deleteButton;
    @FXML
    private VBox ShelfCardBox;
    private Shelf currentShelf;
    @FXML
    private HBox user;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    public void setData(Shelf shelf) {
        shelfName.setText(shelf.getShelfName());
        shelf.setUserID(User.getInstance().getUserId());
        currentShelf = shelf;
        
    }
    
    @FXML
    public void onMouseEntered(MouseEvent event) throws IOException {
        if (event.getSource() == ShelfCardBox) {
        	deleteButton.setDisable(false);
        	deleteButton.setVisible(true);
        }
    }
    
    @FXML
    public void onMouseExited(MouseEvent event) throws IOException {
        if (event.getSource() == ShelfCardBox) {
        	deleteButton.setDisable(true);
        	deleteButton.setVisible(false);
        }
    }
    
    @FXML
    public void onMouseEnteredDelete(MouseEvent event) throws IOException {
        if (event.getSource() == deleteButton) {
        	deleteButton.setOpacity(1);
        }
    }
    @FXML
    public void onMouseExitedDelete(MouseEvent event) throws IOException {
        if (event.getSource() == deleteButton) {
        	deleteButton.setOpacity(0.6);
        }
    }
    @FXML
    void onClickShelf(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShelfDetailsScene.fxml"));
        Parent root = loader.load();
        ShelfDetailController controller = loader.getController();
        Shelf shelf = new Shelf();
        
        shelf.setShelfName(shelfName.getText());
        controller.setData(shelf);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void onClickImage(MouseEvent event) throws IOException {
        if (event.getSource() == deleteButton) {
            if (currentShelf != null) {
            	int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this shelf?", "Delete this shelf.", JOptionPane.YES_NO_OPTION);
            	if(dialogResult == JOptionPane.YES_OPTION) {
            		DAOShelf.getInstance().delete(currentShelf);
            		myShelvesPageController = MyShelvesPageController.getInstance();
                    myShelvesPageController.reloadDataAndRefreshUI();
            	}
            } else {
                // Xử lý khi không có tủ sách hiện tại
            }
        }
     
    }
}