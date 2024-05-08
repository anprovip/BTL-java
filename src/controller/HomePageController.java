package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOBook;
import database.DAOUser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.ChangeScene;
import model.User;
import test.RecommendedBookThread;
import test.RecommendedBookThread.RecommendedBookApplication;

public class HomePageController implements Initializable{
	
	
	@FXML
    private HBox cardLayout;
	@FXML
    private GridPane bookContainer;
	private List<Book> recentlyAdded;
	
	@FXML
    private HBox home;
	

	@FXML
    private ImageView avatarOfUser;
	
    @FXML
    private Label displayName;
	
    @FXML
    private Button createButton;
    
    @FXML
    private VBox homeBox;

    @FXML
    private HBox search;

    @FXML
    private HBox myShelves;

    @FXML
    private HBox user;

    @FXML
    private Pagination pagination;
    
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button loadMoreButton;
    @FXML
    private Button backButton;
    
    private static HomePageController instance;
    
    public static HomePageController getInstance() {
        return instance;
    }

    @FXML
    private GridPane searchContainer;

    @FXML
    private ScrollPane searchScrollPane;
    
    @FXML
    private BorderPane homePageBorderPane;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    private final int itemsPerPage = 10;
    private int currentPage = 1;
    private List<Book> allBooks = new ArrayList<>();
    private List<Node> displayedBooks = new ArrayList<>();
    
    
    public void switchBox(MouseEvent event) throws IOException {
            new ChangeScene(homePageBorderPane, "/views/SearchPageScene.fxml");
    }

    
    private List<Book> getAllBooksFromDatabase() {
    		return DAOBook.getInstance().selectAll();
    }

    	@Override
    	public void initialize(URL arg0, ResourceBundle arg1) {
    		User user = DAOUser.getInstance().selectByUsername(User.getInstance().getUsername());
            
            displayName.setText("@"+user.getDisplayName());
            
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
    		instance = this;
    	    recentlyAdded = new ArrayList<>(getAllBooksFromDatabase()); // Thay đổi cách lấy danh sách sách
    	    new ArrayList<>(getAllBooksFromDatabase());
    	    
    	    // Thêm sự kiện cho nút "Xem thêm"
    	    loadMoreButton.setOnAction(this::loadMore);
    	    backButton.setOnAction(this::goBack);
    	    backButton.setDisable(true); // Vô hiệu hóa nút "Back" khi chưa có gì để quay lại
    	    try {
    	        for (Book value : recentlyAdded) {
    	            FXMLLoader loader = new FXMLLoader();
    	            loader.setLocation(getClass().getResource("/views/card.fxml"));
    	            HBox cardBox = loader.load();
    	            CardController cardController = loader.getController();
    	            cardController.setData(value);
    	            cardLayout.getChildren().add(cardBox);
    	        }

    	        allBooks.addAll(recentlyAdded); // Sử dụng danh sách sách từ cơ sở dữ liệu
    	        showBooks(0, itemsPerPage); // Hiển thị các cuốn sách ban đầu
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	    
    	    new RecommendedBookThread().start();
    	    
    }
    
    private void showBooks(int startIndex, int count) {
        bookContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
        int column = 0;
        int row = 1;
        for (int i = startIndex; i < Math.min(startIndex + count, allBooks.size()); i++) {
            Book book = allBooks.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/book.fxml"));
            
            try {
            	BorderPane bookPane = loader.load();
                BookController bookController = loader.getController();
                bookController.setData(book);

                if (column == 5) {
                    column = 0;
                    row++;
                }
                bookContainer.add(bookPane, column++, row);
                GridPane.setMargin(bookPane, new Insets(15));
                displayedBooks.add(bookPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadMore(ActionEvent event) {
        int startIndex = currentPage * itemsPerPage;
        int remainingBooks = allBooks.size() - startIndex;
        int count = Math.min(remainingBooks, itemsPerPage); // Hiển thị tối đa số sách mỗi trang
        if (count > 0) {
            showBooks(startIndex, count);
            currentPage++;
            if (startIndex + count >= allBooks.size()) {
                loadMoreButton.setDisable(true); // Vô hiệu hóa nút nếu đã hiển thị hết sách
            }
            backButton.setDisable(false); // Bật nút "Back" khi đã load thêm sách
        } else {
            loadMoreButton.setDisable(true); // Vô hiệu hóa nút nếu không còn sách nào để hiển thị
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        if (currentPage <= 1) {
            backButton.setDisable(true); // Nếu là trang đầu tiên, vô hiệu hóa nút "Back"
        } else {
            int startIndex = Math.max(0, (currentPage - 2) * itemsPerPage); // Lấy về trang trước đó
            showBooks(startIndex, itemsPerPage);
            currentPage--; // Giảm trang hiện tại về trang trước đó

            // Bật lại nút "Load More" nếu đã vô hiệu hóa trước đó
            loadMoreButton.setDisable(false);
        }
    }


	public void switchtoUserInformation(MouseEvent e) throws IOException {
		if(e.getSource() == user) {
			new ChangeScene(homePageBorderPane, "/views/UserScene.fxml");
		}
	}
	public void switchtoMyShelves(MouseEvent e) throws IOException {
		if(e.getSource() == myShelves) {
			new ChangeScene(homePageBorderPane, "/views/MyShelvesPageScene.fxml");
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
            myShelvesPageController = MyShelvesPageController.getInstance();
            if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }
	public void reloadDataAndRefreshUI() {
	    
		recentlyAdded = new ArrayList<>(getAllBooksFromDatabase()); // Lấy lại danh sách sách từ cơ sở dữ liệu
	    allBooks.clear(); // Xóa danh sách sách hiện tại
	    allBooks.addAll(recentlyAdded);
	    
	    currentPage = 1;
	    
	    // Hiển thị lại sách trên giao diện
	    int startIndex = (currentPage - 1) * itemsPerPage;
	    showBooks(startIndex, itemsPerPage);
	    
	    // Hiển thị lại nút "Back" nếu cần
	    if (currentPage > 1) {
	        backButton.setDisable(false);
	    } else {
	        backButton.setDisable(true);
	    }
	    
	    // Hiển thị lại nút "Next" nếu cần
	    int remainingBooks = allBooks.size() - (currentPage * itemsPerPage);
	    if (remainingBooks > 0) {
	        loadMoreButton.setDisable(false);
	    } else {
	        loadMoreButton.setDisable(true);
	    }
	    
	}
	public void refreshDisplayName(String currentUsername) {
	    User user = DAOUser.getInstance().selectByUsername(currentUsername);
	    displayName.setText(user.getDisplayName());
	}

	
}