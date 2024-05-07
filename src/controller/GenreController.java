package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Genre;
import model.Review;
import model.User;

public class GenreController {
	private Stage stage;
	private Scene scene;
	
    @FXML
    private Button genreButton;
	
    public Genre currentGenre;
    
	public void setData(Genre genre) {
		genreButton.setText(genre.getGenreName());
        
		currentGenre = genre;
    }
	
    @FXML
    public void onClickButton(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GenreScene.fxml"));
        Parent root = loader.load();
        GenreSceneController controller = loader.getController();
        Genre clickedGenre = new Genre();
        clickedGenre.setGenreName(genreButton.getText());
        controller.setData(clickedGenre);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }

    
}
