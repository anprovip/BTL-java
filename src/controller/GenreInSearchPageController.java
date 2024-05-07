package controller;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Genre;

public class GenreInSearchPageController {
	private Stage stage;
	private Scene scene;
	
    @FXML
    private HBox box;
    
	public Genre currentGenre;

    @FXML
    private Label genreButton;
    
    private String[] colors = {"7d4b32", "36475f", "006450", "e8115b", "608108", "503750", "056952", "8d67ab", "a56752", "777777", "d84000", "ba5d07"};
    
    private Random random = new Random();
    public void setData(Genre genre) {
		genreButton.setText(genre.getGenreName());
		currentGenre = genre;
		String randomColor = colors[random.nextInt(colors.length)];
        box.setStyle("-fx-background-color: #" + randomColor + ";" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10);");
    }

    @FXML
    void onClickButton(MouseEvent event) throws IOException {
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
