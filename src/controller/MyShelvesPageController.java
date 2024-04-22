package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.ChangeScene;

public class MyShelvesPageController {
    @FXML
    private HBox home;
    
    @FXML
    private BorderPane myShelvesPageBorderPane;
    
    @FXML
    private HBox search;

    @FXML
    private HBox user;


	public void switchtoUserInformation(MouseEvent e) throws IOException {
		if(e.getSource() == user) {
			new ChangeScene(myShelvesPageBorderPane, "/views/UserScene.fxml");
		}
	}
	public void switchtoSearch(MouseEvent event) throws IOException {
        new ChangeScene(myShelvesPageBorderPane, "/views/SearchPageScene.fxml");
	}
	public void switchtoHome(MouseEvent e) throws IOException {
		if(e.getSource() == home) {
			new ChangeScene(myShelvesPageBorderPane, "/views/HomePageScene.fxml");
		}
	}
}
