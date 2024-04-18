package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;

public class HomePageController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private HBox cardLayout;
	@FXML
    private GridPane bookContainer;
	private List<Book> recentlyAdded;
	private List<Book> recommended;
	
    @FXML
    private HBox home;

    @FXML
    private VBox homeBox;

    @FXML
    private HBox search;

    @FXML
    private VBox searchBox;
    
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
    
    private final int itemsPerPage = 12;
    private int currentPage = 1;
    private List<Book> allBooks = new ArrayList<>();;
    private List<Node> displayedBooks = new ArrayList<>();
    
    public void switchBox(MouseEvent event) {
    	if(event.getSource() == home) {
    		homeBox.setVisible(true);
    		searchBox.setVisible(false);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == search) {
    		homeBox.setVisible(false);
    		searchBox.setVisible(true);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == myShelves) {
    		homeBox.setVisible(false);
    		searchBox.setVisible(false);
    		myShelvesBox.setVisible(true);
    	}
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(books());
        
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
            
            allBooks.addAll(books()); // Thêm tất cả sách vào allBooks
            showBooks(0, itemsPerPage); // Hiển thị các cuốn sách ban đầu
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showBooks(int startIndex, int count) {
        bookContainer.getChildren().clear(); // Xóa các sách hiện tại trước khi hiển thị sách mới
        int column = 0;
        int row = 1;
        for (int i = startIndex; i < Math.min(startIndex + count, allBooks.size()); i++) {
            Book book = allBooks.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/book.fxml"));
            try {
                VBox bookBox = loader.load();
                BookController bookController = loader.getController();
                bookController.setData(book);

                if (column == 6) {
                    column = 0;
                    row++;
                }
                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
                displayedBooks.add(bookBox);
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

	private List<Book> recentlyAdded(){
		List<Book> ls = new ArrayList<>();
		
		Book book = new Book();
		book.setName("RICH DAD\nPOOR DAD");
		book.setImageSrc("/img/images.png");
		book.setAuthor("Robert T.Kiyosaki");
		ls.add(book);
		
		book = new Book();
		book.setName("THE WARREN\nBUFFET WAY");
		book.setImageSrc("/img/book3.png");
		book.setAuthor("Robert G.Hagstorm");
		ls.add(book);
		
		book = new Book();
		book.setName("THE RICHEST\nMAN IN BABYLON");
		book.setImageSrc("/img/book2.png");
		book.setAuthor("George Samuel Clason");
		ls.add(book);
		
		return ls;
	}
	
	private List<Book> books(){
		List<Book> ls = new ArrayList<>();
		
		Book book = new Book();
		book.setName("ZERO TO ONE");
		book.setImageSrc("/img/book4.png");
		book.setAuthor("Peter Thiel");
		ls.add(book);
		
		book = new Book();
		book.setName("Money Master The Game");
		book.setImageSrc("/img/book5.png");
		book.setAuthor("Tony Robbins");
		ls.add(book);
		
		book = new Book();
		book.setName("The 100$ Startup");
		book.setImageSrc("/img/book6.png");
		book.setAuthor("Chris Guillebeau");
		ls.add(book);
		
		book = new Book();
		book.setName("Cracking The Coding Interview");
		book.setImageSrc("/img/book7.png");
		book.setAuthor("Gayle Laakmann McDowell");
		ls.add(book);
		
		book = new Book();
		book.setName("Evangelion");
		book.setImageSrc("/img/book8.png");
		book.setAuthor("Anno Hideaki & Yoshiyuki Sadamoto");
		ls.add(book);
		
		book = new Book();
		book.setName("Bakemonogatari");
		book.setImageSrc("/img/book9.png");
		book.setAuthor("Nisio Isin");
		ls.add(book);
		
		book = new Book();
		book.setName("Java For Beginners Guide");
		book.setImageSrc("/img/book10.png");
		book.setAuthor("Josh Thompsons");
		ls.add(book);
		
		book = new Book();
		book.setName("Man's search for meaning");
		book.setImageSrc("/img/book11.png");
		book.setAuthor("Viktor E. Frankl");
		ls.add(book);
		
		book = new Book();
		book.setName("The Intelligent Investor");
		book.setImageSrc("/img/book12.png");
		book.setAuthor("Benjamin Graham");
		ls.add(book);
		
		book = new Book();
		book.setName("The Art of War");
		book.setImageSrc("/img/book13.png");
		book.setAuthor("Sun Tzu");
		ls.add(book);
		
		book = new Book();
		book.setName("Sense And Sensibility");
		book.setImageSrc("/img/book14.png");
		book.setAuthor("Jane Austen");
		ls.add(book);
		
		book = new Book();
		book.setName("Great Expectations");
		book.setImageSrc("/img/book15.png");
		book.setAuthor("Charles Dickens");
		ls.add(book);
		
		book = new Book();
		book.setName("Jane Eyre");
		book.setImageSrc("/img/book16.png");
		book.setAuthor("Charlotte Brontë");
		ls.add(book);
		
		book = new Book();
		book.setName("The Canterville Ghost");
		book.setImageSrc("/img/book17.png");
		book.setAuthor("Oscar Wilde");
		ls.add(book);
		
		book = new Book();
		book.setName("Anna Karenina");
		book.setImageSrc("/img/book18.png");
		book.setAuthor("Leo Tolstoy");
		ls.add(book);
		
		book = new Book();
		book.setName("Re:Zero");
		book.setImageSrc("/img/book19.png");
		book.setAuthor("Tappei Nagatsuki");
		ls.add(book);
		return ls;
	}
	
	public void switchtoUserInformation(MouseEvent e) throws IOException {
		if(e.getSource() == user) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserScene.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root, 1515, 770);
			stage.setTitle("Goodreads");
			stage.setScene(scene);
			stage.show();
		}
	}
	
}