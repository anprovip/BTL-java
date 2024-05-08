package test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecommendedBookThread extends Thread {
    private int count = 0;
    private final int MAX_COUNT = 3; // Số lần lặp lại tối đa
    private static Stage currentStage; // Biến để lưu cửa sổ hiện tại
    private boolean running = true; // Biến để kiểm tra luồng có đang chạy không

    @Override
    public void run() {
        while (count < MAX_COUNT && running) {
            try {
                // Tạo FXMLLoader để load scene RecommendedBook.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecommendedBook.fxml"));
                Parent root = loader.load();

                // Tạo scene
                Scene scene = new Scene(root);

                // Kiểm tra nếu có cửa sổ đang mở và chưa đóng thì đóng cửa sổ đó
                if (currentStage != null && currentStage.isShowing()) {
                    Platform.runLater(() -> currentStage.close());
                }

                // Hiển thị cửa sổ trên FX application thread
                Platform.runLater(() -> {
                    // Tạo Stage và hiển thị scene trên Stage
                    currentStage = new Stage();
                    currentStage.setScene(scene);
                    currentStage.show();
                });

                // Tạm dừng luồng trong 15 giây (15000 milliseconds)
                Thread.sleep(15000);

                count++; // Tăng biến đếm sau mỗi lần lặp
            } catch (InterruptedException e) {
                // Xử lý khi bị ngắt quãng
                System.out.println("RecommendedBookThread interrupted.");
                running = false; // Đặt biến running thành false để kết thúc vòng lặp
                // Thêm các bước xử lý khác nếu cần thiết
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức để đóng cửa sổ hiện tại của luồng
    public void closeCurrentStage() {
        if (currentStage != null && currentStage.isShowing()) {
            Platform.runLater(() -> currentStage.close());
        }
    }
}
