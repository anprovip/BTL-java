package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOShelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ChangeScene;
import model.Shelf;
import model.User;

public class MyShelvesPageController implements Initializable{
    @FXML
    private HBox home;
    
    @FXML
    private BorderPane myShelvesPageBorderPane;
    
    @FXML
    private HBox search;

    @FXML
    private HBox user;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button createButton;
    
    @FXML
    private VBox addShelfBox;
    
    @FXML
    private ImageView avatarOfUser;
    
    @FXML
    private Label displayName;
    
    @FXML
    private GridPane shelfContainer;
    
    private final int itemsPerPage = 9;
    private int currentPage = 1;
    private List<Shelf> recentlyAddedShelf;
    private List<Shelf> allShelves = new ArrayList<>();
    private List<Node> displayedShelves = new ArrayList<>();
    
    private static MyShelvesPageController instance;
    
    public static MyShelvesPageController getInstance() {
        return instance;
    }
    
    private List<Shelf> getAllShelvesFromDatabase(User user) {
			return DAOShelf.getInstance().selectByCondition(Long.toString(user.getUserId()));
			
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    		User user = User.getInstance();
    		recentlyAddedShelf = new ArrayList<>(getAllShelvesFromDatabase(user));
    	    // Thêm sự kiện cho nút "Xem thêm"
    	    nextButton.setOnAction(this::loadMore);
    	    backButton.setOnAction(this::goBack);
    	    backButton.setDisable(true); // Vô hiệu hóa nút "Back" khi chưa có gì để quay lại

    	    allShelves.addAll(recentlyAddedShelf); // Sử dụng danh sách sách từ cơ sở dữ liệu
    	    if(allShelves.size()<=itemsPerPage) nextButton.setDisable(true);
			showShelves(0, itemsPerPage); // Hiển thị các cuốn sách ban đầu
			reloadDataAndRefreshUI();
			instance = this;
			
	        displayName.setText(user.getDisplayName());
            
            if (user != null) {
            	Blob imageBlob = user.getImageUser();
                if (imageBlob != null) {
                    try {
                        // Chuyển đổi Blob thành mảng byte
                        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                        // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                        Image image = new Image(new ByteArrayInputStream(imageData));
                        avatarOfUser.setImage(image);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
			
    }
    
    private void showShelves(int startIndex, int count) {
    	if (shelfContainer == null) {
            System.err.println("Error: shelfContainer is null.");
            return;
        }
    	shelfContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
        int column = 0;
        int row = 1;
        for (int i = startIndex; i < Math.min(startIndex + count, allShelves.size()); i++) {
            Shelf shelf = allShelves.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShelfCard.fxml"));
            
            try {
            	BorderPane shelfPane = loader.load();
                ShelfController shelfController = loader.getController();
                shelfController.setData(shelf);
                shelfController.setAddShelfListVisible(false);
                shelfController.setAddShelfListDisable(true);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                shelfContainer.add(shelfPane, column++, row);
                GridPane.setMargin(shelfPane, new Insets(25));
                displayedShelves.add(shelfPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadMore(ActionEvent event) {
        int startIndex = currentPage * itemsPerPage;
        int remainingBooks = allShelves.size() - startIndex;
        int count = Math.min(remainingBooks, itemsPerPage); // Hiển thị tối đa số sách mỗi trang
        if (count > 0) {
            showShelves(startIndex, count);
            currentPage++;
            if (startIndex + count >= allShelves.size()) {
                nextButton.setDisable(true); // Vô hiệu hóa nút nếu đã hiển thị hết sách
            }
            backButton.setDisable(false); // Bật nút "Back" khi đã load thêm sách
        } else {
            nextButton.setDisable(true); // Vô hiệu hóa nút nếu không còn sách nào để hiển thị
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        
        if (currentPage > 1) {
        	int startIndex = Math.max(0, (currentPage - 2) * itemsPerPage); // Lấy về trang trước đó
        	showShelves(startIndex, itemsPerPage);
            currentPage--; // Giảm trang hiện tại về trang trước đó

            // Bật lại nút "Load More" nếu đã vô hiệu hóa trước đó
            nextButton.setDisable(false);
            
        }
        backButton.setDisable(currentPage <= 1);
    }

    
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
	@FXML
    public void onClickAdd(ActionEvent event) {
		try {
            // Tạo một Stage mới cho cửa sổ popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add your Shelf");

            // Load nội dung từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddShelfPopup.fxml"));
            VBox root = loader.load();

            // Gán controller cho cửa sổ popup
            //AddShelfPopupController controller = loader.getController();
            
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
            
            reloadDataAndRefreshUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }
	public void reloadDataAndRefreshUI() {
	    // Reload dữ liệu từ cơ sở dữ liệu hoặc làm bất kỳ công việc nào cần thiết để cập nhật dữ liệu mới
	    User user = User.getInstance();
	    allShelves.clear();
	    allShelves.addAll(getAllShelvesFromDatabase(user));
	    
	    currentPage = 1;
	    
	    // Hiển thị lại sách trên giao diện
	    int startIndex = (currentPage - 1) * itemsPerPage;
	    showShelves(startIndex, itemsPerPage);
	    
	    // Hiển thị lại nút "Back" nếu cần
	    if (currentPage > 1) {
	        backButton.setDisable(false);
	    } else {
	        backButton.setDisable(true);
	    }
	    
	    // Hiển thị lại nút "Next" nếu cần
	    int remainingBooks = allShelves.size() - (currentPage * itemsPerPage);
	    if (remainingBooks > 0) {
	        nextButton.setDisable(false);
	    } else {
	        nextButton.setDisable(true);
	    }
	    
	}


}
