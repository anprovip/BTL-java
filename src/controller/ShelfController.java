package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.DAOShelf;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Shelf;

public class ShelfController implements Initializable {
    @FXML
    private Label shelfName;
    
    private Stage stage;  
    private Scene scene;

    @FXML
    private ImageView deleteButton;
    public ImageView getDeleteButton() {
        return deleteButton;
    }
    @FXML
    private ImageView addShelfList;
    
    @FXML
    private VBox ShelfCardBox;
    private Shelf currentShelf;
    @FXML
    private HBox user;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    private static ShelfController instance;
    
    public static ShelfController getInstance() {
        return instance;
    }
    public void setData(Shelf shelf) {
        shelfName.setText(shelf.getShelfName());
        //shelf.setUserID(User.getInstance().getUserId());
        currentShelf = shelf;
        System.out.println(currentShelf.getUserID());
        
    }
    
    @FXML
    public void onMouseEntered(MouseEvent event) throws IOException {
    	/*
        if (event.getSource() == ShelfCardBox) {
        	deleteButton.setDisable(false);
        	deleteButton.setVisible(true);
        }
        */
    }
    
    @FXML
    public void onMouseExited(MouseEvent event) throws IOException {
        /*
    	if (event.getSource() == ShelfCardBox) {
        	deleteButton.setDisable(true);
        	deleteButton.setVisible(false);
        }
        */
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
        	deleteButton.setOpacity(0.5);
        }
    }
    @FXML
    public void onMouseEnteredAdd(MouseEvent event) throws IOException {
        if (event.getSource() == addShelfList) {
        	addShelfList.setOpacity(1);
        }
    }
    @FXML
    public void onMouseExitedAdd(MouseEvent event) throws IOException {
        if (event.getSource() == addShelfList) {
        	addShelfList.setOpacity(0.5);
        }
    }
    @FXML
    void onClickShelf(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShelfDetailsScene.fxml"));
        Parent root = loader.load();
        ShelfDetailController controller = loader.getController();
        System.out.println(currentShelf.getUserID() + " Sao luc truoc no la nhu nay ma");
        controller.setData(currentShelf);
        System.out.println(currentShelf.getUserID() + " Sao luc sau no lai la nhu nay ????");
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
    @FXML
    public void onClickImageAdd(MouseEvent event) throws IOException {
        if (event.getSource() == addShelfList) {
            if (currentShelf != null) {
            	int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this shelf?", "Add this shelf.", JOptionPane.YES_NO_OPTION);
            	if(dialogResult == JOptionPane.YES_OPTION) {
            		DAOShelf.getInstance().otherUserInsertShelf(currentShelf);
                    // Sau khi thêm xong, cập nhật lại dữ liệu và làm mới giao diện
                    myShelvesPageController = MyShelvesPageController.getInstance();
                    if(myShelvesPageController != null) {
                    	myShelvesPageController.reloadDataAndRefreshUI();
                    }
            	}
            } else {
                // Xử lý khi không có tủ sách hiện tại
            }
        }
     
    }
    
    public void setDeleteButtonVisible(boolean visible) {
        deleteButton.setVisible(visible);
    }

    public void setDeleteButtonDisable(boolean disable) {
        deleteButton.setDisable(disable);
    }
    
    public void setAddShelfListVisible(boolean visible) {
    	addShelfList.setVisible(visible);
    }

    public void setAddShelfListDisable(boolean disable) {
    	addShelfList.setDisable(disable);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		
	}
}