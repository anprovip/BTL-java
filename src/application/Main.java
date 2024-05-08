package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScene.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 1440, 900);
			
<<<<<<< HEAD
	        stage.setTitle("Goodreads");
=======
	        stage.setTitle("Tacoreads");
>>>>>>> giang2004
			stage.setScene(scene);
			stage.setResizable(false);
			Image icon = new Image(getClass().getResourceAsStream("/img/taco.png"));
			stage.getIcons().add(icon);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

