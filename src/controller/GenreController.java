package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Genre;

public class GenreController implements Initializable {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

    
}
