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
import database.DAOShelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import model.Shelf;
import model.User;

public class ShelfDetailController implements Initializable{
	@FXML
    private Label shelfName;
    
    @FXML
    private Button createButton;

    @FXML
    private HBox home;

    @FXML
    private HBox myShelves;

    @FXML
    private BorderPane myShelvesPageBorderPane;

    @FXML
    private HBox search;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private ScrollPane shelfScrollPane;
    
    @FXML
    private GridPane bookInShelfContainer;
    
    @FXML
    private ImageView avatarOfUser;
    
    @FXML
    private Label displayName;

    @FXML
    private HBox user;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    private final int itemsPerPage = 5;
    private int currentPage = 1;
    private List<Book> allBooksInShelf = new ArrayList<>();
    private List<Book> recentlyAdded;
    
    private long userID;
    
    public void setData(Shelf shelf) {
    	nextButton.setDisable(false);
    	System.out.println("Nhận được dữ liệu khi click: " + shelf.getShelfID());
    	
    	System.out.println(shelf.getUserID()+" LUC SANG SET DATA CUA SHELF DETAILS");
    	
    	Shelf updatedShelf = DAOShelf.getInstance().selectByID(shelf);
    	
    	System.out.println(updatedShelf.getUserID()+" sau khi xu ly bang db");
    	userID = shelf.getUserID();
    	
    	recentlyAdded = DAOBook.getInstance().selectByShelfName(updatedShelf.getShelfName(), updatedShelf.getUserID());
    	System.out.println(updatedShelf.getShelfName());
    	
    	System.out.println(updatedShelf.getBookID());
    	allBooksInShelf.addAll(recentlyAdded);
    	if(allBooksInShelf.size()<=itemsPerPage) nextButton.setDisable(true);
	    backButton.setDisable(true);
        showBooksInShelf(0, itemsPerPage);
        /*if (shelf != null) {
            
        } else {
            // Xử lý khi không tìm thấy tủ sách
        }
        */
    }
    private void showBooksInShelf(int startIndex, int count) {
    	bookInShelfContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
        System.out.println("Co den day khong");
        int column = 0;
        int row = 1;
        for (int i = startIndex; i < Math.min(startIndex + count, allBooksInShelf.size()); i++) {
            Book book = allBooksInShelf.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookCardInShelf.fxml"));
            
            try {
            	BorderPane bookInShelfPane = loader.load();
                BookInShelfController bookInShelfController = loader.getController();
                System.out.println(book.getName());
                bookInShelfController.setData(book, userID);
                System.out.println(book.getName());
                bookInShelfContainer.add(bookInShelfPane, column, row++);
                GridPane.setMargin(bookInShelfPane, new Insets(15));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void loadMore(ActionEvent event) {
        int startIndex = currentPage * itemsPerPage;
        int remainingBooks = allBooksInShelf.size() - startIndex;
        int count = Math.min(remainingBooks, itemsPerPage); // Hiển thị tối đa số sách mỗi trang
        if (count > 0) {
            showBooksInShelf(startIndex, count);
            currentPage++;
            if (startIndex + count >= allBooksInShelf.size()) {
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
        	showBooksInShelf(startIndex, itemsPerPage);
            currentPage--; // Giảm trang hiện tại về trang trước đó

            // Bật lại nút "Load More" nếu đã vô hiệu hóa trước đó
            nextButton.setDisable(false);
            
        }
        backButton.setDisable(currentPage <= 1);
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
            myShelvesPageController.reloadDataAndRefreshUI();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
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
	public void switchtoMyShelves(MouseEvent e) throws IOException {
		if(e.getSource() == myShelves) {
			new ChangeScene(myShelvesPageBorderPane, "/views/MyShelvesPageScene.fxml");
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nextButton.setOnAction(this::loadMore);
	    backButton.setOnAction(this::goBack);
	    backButton.setDisable(true);
	    myShelvesPageController = MyShelvesPageController.getInstance();
	    
	    User user = User.getInstance();
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
}