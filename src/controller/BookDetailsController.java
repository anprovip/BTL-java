package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import model.ChangeScene;
import model.Review;
import database.DAOBook;
import database.DAOReview;

public class BookDetailsController implements Initializable{
    
	@FXML
    private BorderPane DBookBorderPane;

    @FXML
    private Label authorName;

    @FXML
    private Button backButton;

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
    
    private final int itemsPerPage = 5;
    private int currentPage = 1;
    private List<Review> allReviews = new ArrayList<>();
    private List<Review> recentlyAdded;
    private static String isbn;
    
    private List<Review> getAllReviewsFromDatabase(String bookId) {
    	
		return DAOReview.getInstance().selectByCondition(bookId);
    }

    public void setData(Book book) {
        System.out.println("Nhận được dữ liệu khi click: " + book.getBookID());
        book = DAOBook.getInstance().selectByID(book);
        DAOReview.getInstance().selectByCondition(book.getBookID());
        if (book != null) {
            bookName.setText(book.getName());
            authorName.setText(book.getAuthor());
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
        } else {
            // Xử lý khi không tìm thấy sách
        }
    }
    
    
    @FXML
    private void onClickAddReview(MouseEvent event) {
        String reviewText = reviewTextField.getText();
        // Phải có ISBN và user ID để thêm review vào cơ sở dữ liệu
        String ISBN = ""; // Lấy ISBN từ đối tượng Book, tạm thời để là chuỗi rỗng
        int userID = 0; // Lấy user ID từ người dùng hoặc từ hệ thống, tạm thời để là 0
        int rating = 5; // Đánh giá mặc định, có thể thay đổi theo ý của người dùng

        if (!reviewText.isEmpty() && !ISBN.isEmpty() && userID != 0) {
            // Tạo đối tượng Review từ dữ liệu nhập vào
            Review review = new Review();
            review.setISBN(ISBN);
            review.setUserId(userID);
            review.setReviewText(reviewText);
            review.setRating(rating);

            DAOReview.getInstance().insert(review);


        } else {
            // Xử lý khi dữ liệu nhập vào không hợp lệ
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		recentlyAdded = new ArrayList<>(getAllReviewsFromDatabase(isbn)); // Thay đổi cách lấy danh sách sách
		nextButton.setOnAction(this::loadMore);
	    backButton.setOnAction(this::goBack);
	    backButton.setDisable(true);
	    
	    allReviews.addAll(recentlyAdded); // Sử dụng danh sách sách từ cơ sở dữ liệu
        showReviews(0, itemsPerPage);
	}
	private void showReviews(int startIndex, int count) {
        reviewContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
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
        if (currentPage <= 1) {
            backButton.setDisable(true); // Nếu là trang đầu tiên, vô hiệu hóa nút "Back"
        } else {
            int startIndex = Math.max(0, (currentPage - 2) * itemsPerPage); // Lấy về trang trước đó
            showReviews(startIndex, itemsPerPage);
            currentPage--; // Giảm trang hiện tại về trang trước đó

            // Bật lại nút "Load More" nếu đã vô hiệu hóa trước đó
            nextButton.setDisable(false);
        }
    }
    
}
