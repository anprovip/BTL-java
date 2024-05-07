package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class ChangeScene {
    private static Map<String, BorderPane> scenes = new HashMap<>();

    public ChangeScene(BorderPane currentBorderPane, String fxmlFile) throws IOException {
        if (!scenes.containsKey(fxmlFile)) {
            BorderPane nextBorderPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
            scenes.put(fxmlFile, nextBorderPane);
        }

        BorderPane nextBorderPane = scenes.get(fxmlFile);
        BorderPane newRoot = new BorderPane(); // Tạo một BorderPane mới

        // Thêm nextBorderPane vào newRoot và hiển thị nó
        newRoot.setCenter(nextBorderPane);
        nextBorderPane.setVisible(true);

        // Thay đổi root của Scene thành newRoot
        currentBorderPane.getScene().setRoot(newRoot);
    }
    
    public static void clearScenes() {
        scenes.clear();
    }
}


