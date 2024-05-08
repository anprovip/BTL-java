package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.ChangeScene;
import model.Genre;
import model.Review;
import model.Shelf;
import model.User;
import database.DAOBook;
import database.DAOGenre;
import database.DAOReview;
import database.DAOShelf;

public class BookDetailsController implements Initializable {
    
	@FXML
    private BorderPane DBookBorderPane;

    @FXML
    private Label authorName;

    @FXML
    private Label averageRating;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label BookID;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private HBox home;

    @FXML
    private VBox homeBox;

    @FXML
    private HBox myShelves;


    @FXML
    private Button createButton;

    @FXML
    private Button nextButton;

    @FXML
    private GridPane reviewContainer;

    @FXML
    private ScrollPane reviewScrollPane;

    @FXML
    private TextField reviewTextField;

    @FXML
    private HBox search;
    
    @FXML
    private Button addButton;
    
    @FXML
    private ImageView editButton;
    
    @FXML
    private Label createDefaultShelf;
    

    @FXML
    private TextArea summaryOfBook;
    
    @FXML
    private ImageView avatarOfUser;
    
    @FXML
    private Label displayName;
    
    @FXML
    
    private Stage stage;
	private Scene scene;
    
	@FXML
    private ListView<String> genresList;
    
	private List<Genre> allGenres = new ArrayList<>();
    private List<Genre> listGenres;
    
    @FXML
    private ChoiceBox<String> userRate;
    private String[] rating = {"1 Star", "2 Stars","3 Stars","4 Stars","5 Stars"};
    private final int itemsPerPage = 5;
    private int currentPage = 1;
    private List<Review> allReviews = new ArrayList<>();
    private List<Review> recentlyAdded;

    public static Book currentBook;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    private HomePageController homePageController;
    @FXML
    private GridPane genreContainer;
    
    private List<Genre> getAllGenresFromDatabase() {
    	return DAOGenre.getInstance().selectByCondition(currentBook.getBookID());
    }
    public void showGenres() {
        int column = 0;
        int row = 1;
        for (int i = 0; i < allGenres.size(); i++) {
            Genre genre = allGenres.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GenreCard.fxml"));
            
            try {
            	HBox genrePane = loader.load();
                GenreController genreController = loader.getController();
                genreController.setData(genre);

                if (column == 12) {
                    column = 0;
                    row++;
                }
                genreContainer.add(genrePane, column++, row);
                GridPane.setMargin(genrePane, new Insets(5));
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public void setData(Book book) {
    	nextButton.setDisable(false);
    	BookID.setText(book.getBookID());
        System.out.println("Nhận được dữ liệu khi click: " + book.getBookID());
        currentBook = book;
        book = DAOBook.getInstance().selectByID(book);
        recentlyAdded = DAOReview.getInstance().selectByCondition(book.getBookID());
        System.out.println(recentlyAdded.size());
        allReviews.addAll(recentlyAdded); 
        if(allReviews.size()<=itemsPerPage) nextButton.setDisable(true);
	    backButton.setDisable(true);
        showReviews(0, itemsPerPage);
        listGenres = new ArrayList<>(getAllGenresFromDatabase());
        allGenres.addAll(listGenres);
        showGenres();
        if (book != null) {
            bookName.setText(book.getName());
            authorName.setText(book.getAuthor());
            float averageRatingValue = book.getAverageRating();
            String formattedRating = String.format("%.1f", averageRatingValue);
            averageRating.setText(formattedRating);

            summaryOfBook.setText(book.getSummary());
            
            Blob imageBlob = book.getImageBook();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    bookImage.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            showGenres();
          
        } else {
            // Xử lý khi không tìm thấy sách
        }
    }
    
    
    @FXML
    void onClickAdd(MouseEvent event) {
        String reviewText = reviewTextField.getText();
        
        if (!reviewText.isEmpty()) {
            // Tạo đối tượng Review từ dữ liệu nhập vào
            Review review = new Review();
            review.setISBN(BookID.getText());
            review.setReviewText(reviewText);
            review.setRating((int)userRate.getValue().charAt(0)-48);
            
            DAOReview.getInstance().insert(review);
            DAOBook.getInstance().updateRate(BookID.getText());
            JOptionPane.showMessageDialog(null, "Add review successfully!");
            System.out.println("co update binh luan khong?");
            reloadReviewData();
            if(homePageController!=null) {
            	homePageController.reloadDataAndRefreshUI();
            }
            
        } else {
        	JOptionPane.showMessageDialog(null, "Please make sure you do not leave the review blank!");
        }
    }

    public void reloadReviewData() {
        // Xóa tất cả các đánh giá hiện tại
        allReviews.clear();
        recentlyAdded = DAOReview.getInstance().selectByCondition(currentBook.getBookID());
        allReviews.addAll(recentlyAdded);
        
        int startIndex = (currentPage - 1) * itemsPerPage;
        showReviews(startIndex, itemsPerPage);

        if (currentPage > 1) {
	        backButton.setDisable(false);
	    } else {
	        backButton.setDisable(true);
	    }
	    
	    // Hiển thị lại nút "Next" nếu cần
	    int remainingBooks = allReviews.size() - (currentPage * itemsPerPage);
	    if (remainingBooks > 0) {
	        nextButton.setDisable(false);
	    } else {
	        nextButton.setDisable(true);
	    }
        
    }
    
    @FXML
    void onClickHome(MouseEvent event) throws IOException {
    	
    	new ChangeScene(DBookBorderPane, "/views/HomePageScene.fxml");
    	
    }

    @FXML
    void onClickSearch(MouseEvent event) throws IOException {
    	new ChangeScene(DBookBorderPane, "/views/SearchPageScene.fxml");
    }

    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	new ChangeScene(DBookBorderPane, "/views/UserScene.fxml");
    }
    public void switchtoMyShelves(MouseEvent e) throws IOException {
		new ChangeScene(DBookBorderPane, "/views/MyShelvesPageScene.fxml");

	}
    @FXML
    void onClickAuthor(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorScene.fxml"));
        Parent root = loader.load();
        AuthorController controller = loader.getController();
        Author clickedAuthor = new Author();
        clickedAuthor.setAuthorName(authorName.getText());
        controller.setData(clickedAuthor);
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();
    }
	private void showReviews(int startIndex, int count) {
        reviewContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
        //System.out.println("Co den day khong");
        int column = 0;
        int row = 1;
        for (int i = startIndex; i < Math.min(startIndex + count, allReviews.size()); i++) {
            Review review = allReviews.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CardReview.fxml"));
            
            try {
            	HBox reviewPane = loader.load();
                ReviewController reviewController = loader.getController();
                reviewController.setData(review);
                reviewContainer.add(reviewPane, column, row++);
                GridPane.setMargin(reviewPane, new Insets(15));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadMore(ActionEvent event) {
        int startIndex = currentPage * itemsPerPage;
        int remainingBooks = allReviews.size() - startIndex;
        int count = Math.min(remainingBooks, itemsPerPage); // Hiển thị tối đa số sách mỗi trang
        if (count > 0) {
            showReviews(startIndex, count);
            currentPage++;
            if (startIndex + count >= allReviews.size()) {
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
        	showReviews(startIndex, itemsPerPage);
            currentPage--; // Giảm trang hiện tại về trang trước đó

            // Bật lại nút "Load More" nếu đã vô hiệu hóa trước đó
            nextButton.setDisable(false);
            
        }
        backButton.setDisable(currentPage <= 1);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nextButton.setOnAction(this::loadMore);
	    backButton.setOnAction(this::goBack);
	    backButton.setDisable(true);
		userRate.getItems().addAll(rating);
		myShelvesPageController = MyShelvesPageController.getInstance();
		homePageController = HomePageController.getInstance();
		
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
	@FXML
    public void onClickAddShelf(ActionEvent event) {
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
            if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }
	@FXML
    public void onClickAddShelfDefault(MouseEvent event) {
		if(event.getSource() == createDefaultShelf) {
			Shelf shelf = new Shelf();
    		shelf.setShelfName("Want-to-read");
    		shelf.setBookID(currentBook.getBookID());
    		DAOShelf.getInstance().insert(shelf);
    		if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
            }
		}
    }
	@FXML
    public void onClickAddChoice(MouseEvent event) {
		if(event.getSource() == editButton) {
			try {

	            Stage popupStage = new Stage();
	            popupStage.initModality(Modality.APPLICATION_MODAL);
	            popupStage.setTitle("Add your Book to Shelf");

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddBookToShelfPopup.fxml"));
	            VBox root = loader.load();

	            popupStage.setScene(new Scene(root));
	            popupStage.showAndWait();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
    }
	
}
