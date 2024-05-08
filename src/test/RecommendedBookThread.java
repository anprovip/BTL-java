package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecommendedBookThread extends Thread {
    private int count = 0;
    private final int MAX_COUNT = 5; // Số lần lặp lại tối đa

    @Override
    public void run() {
        while (count < MAX_COUNT) {
            try {
                // Tạo FXMLLoader để load scene RecommendedBook.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecommendedBook.fxml"));
                Parent root = loader.load();

                // Tạo scene
                Scene scene = new Scene(root);

                // Hiển thị cửa sổ trên FX application thread
                Platform.runLater(() -> {
                    // Tạo Stage và hiển thị scene trên Stage
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                });

                // Tạm dừng luồng trong 15 giây (15000 milliseconds)
                Thread.sleep(15000);

                count++; // Tăng biến đếm sau mỗi lần lặp
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   public static void main(String[] args) {
        // Khởi động ứng dụng JavaFX để có thể sử dụng JavaFX Toolkit
        Application.launch(RecommendedBookApplication.class, args);
    } 

    // Ứng dụng chính của bạn để khởi chạy ứng dụng JavaFX
    public static class RecommendedBookApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // Khởi động luồng RecommendedBookThread để hiển thị cửa sổ RecommendedBook
            new RecommendedBookThread().start();
        }
    }
}

