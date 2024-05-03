package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOBook;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.ChangeScene;

public class SearchPageController implements Initializable{
	
	
	@FXML
    private HBox cardLayout;
	@FXML
    private GridPane bookContainer;
	
	private List<Book> listBook;
	
	@FXML
    private HBox home;

    @FXML
    private HBox search;
    

    @FXML
    private Button createButton;
    
    @FXML
    private TextField searchTerm;
    
    @FXML
    private ImageView searchIcon;
    
    @FXML
    private HBox myShelves;

    @FXML
    private VBox myShelvesBox;
    
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
    
    @FXML
    private HBox searchBox;

    @FXML
    private GridPane searchContainer;

    @FXML
    private ScrollPane searchScrollPane;
    
    @FXML
    private BorderPane searchPageBorderPane;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    private final int itemsPerPage = 10;
    private int currentPage = 1;
    private List<Book> allBooks = new ArrayList<>();;
    private List<Node> displayedBooks = new ArrayList<>();
    

    public void switchBox(MouseEvent event) throws IOException {
                new ChangeScene(searchPageBorderPane, "/views/HomePageScene.fxml");
    }

    private List<Book> getAllBooksFromDatabase() {
    	return DAOBook.getInstance().selectAll();
    }

    	

    	@Override
    	public void initialize(URL arg0, ResourceBundle arg1) {
    	    listBook = new ArrayList<>(getAllBooksFromDatabase());

    	    loadMoreButton.setOnAction(this::loadMore);
    	    backButton.setOnAction(this::goBack);
    	    backButton.setDisable(true);

    	    allBooks.addAll(listBook);
			showBooks(0, itemsPerPage); 
			myShelvesPageController = MyShelvesPageController.getInstance();
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
        if (startIndex < allBooks.size()) { // Kiểm tra số lượng sách còn lại
            int remainingBooks = allBooks.size() - startIndex;
            int count = Math.min(remainingBooks, itemsPerPage); // Hiển thị tối đa số sách mỗi trang
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
			new ChangeScene(searchPageBorderPane, "/views/UserScene.fxml");
	}
	
	public void switchtoMyShelves(MouseEvent e) throws IOException {
			new ChangeScene(searchPageBorderPane, "/views/MyShelvesPageScene.fxml");
	}
	
    @FXML
    private void onClickSearch(MouseEvent event) {
        performSearch();
    }

    @FXML
    private void onPressedEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            performSearch();
        }
    }

    private void performSearch() {
        String term = searchTerm.getText().trim();
        currentPage = 1;
        loadMoreButton.setDisable(false);
        if (!term.isEmpty()) {
            allBooks.clear();
            listBook = DAOBook.getInstance().selectByCondition(term);
            allBooks.addAll(listBook);  
    	    if(allBooks.size()<=itemsPerPage) loadMoreButton.setDisable(true);
    	    backButton.setDisable(true);
    	    showBooks(0, itemsPerPage);
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
            myShelvesPageController.reloadDataAndRefreshUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }

	
}