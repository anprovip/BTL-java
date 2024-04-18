package model;


import java.io.IOException;
import java.util.Objects;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class ChangeScene {
  public ChangeScene(BorderPane currentBorderPane,String fxmlFile) throws IOException {
	  BorderPane nextBorderPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
	  currentBorderPane.getChildren().removeAll();
	  currentBorderPane.getChildren().setAll(nextBorderPane);
  }
}