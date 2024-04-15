package application;

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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

public class Controller2 implements Initializable{
	
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
    private HBox browse;

    @FXML
    private VBox browseBox;

    @FXML
    private HBox categories;

    @FXML
    private VBox categoriesBox;

    @FXML
    private HBox reading;

    @FXML
    private VBox readingBox;

    @FXML
    private HBox topBooks;

    @FXML
    private VBox topBooksBox;
    
    @FXML
    private HBox saved;

    @FXML
    private VBox savedBox;
    
    @FXML
    private HBox myShelves;

    @FXML
    private VBox myShelvesBox;
    
    @FXML
    private HBox user;
    
    public void switchBox(MouseEvent event) {
    	if(event.getSource() == browse) {
    		browseBox.setVisible(true);
    		topBooksBox.setVisible(false);
    		categoriesBox.setVisible(false);
    		readingBox.setVisible(false);
    		savedBox.setVisible(false);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == topBooks) {
    		browseBox.setVisible(false);
    		topBooksBox.setVisible(true);
    		categoriesBox.setVisible(false);
    		readingBox.setVisible(false);
    		savedBox.setVisible(false);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == categories) {
    		browseBox.setVisible(false);
    		topBooksBox.setVisible(false);
    		categoriesBox.setVisible(true);
    		readingBox.setVisible(false);
    		savedBox.setVisible(false);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == reading) {
    		browseBox.setVisible(false);
    		topBooksBox.setVisible(false);
    		categoriesBox.setVisible(false);
    		readingBox.setVisible(true);
    		savedBox.setVisible(false);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == saved) {
    		browseBox.setVisible(false);
    		topBooksBox.setVisible(false);
    		categoriesBox.setVisible(false);
    		readingBox.setVisible(false);
    		savedBox.setVisible(true);
    		myShelvesBox.setVisible(false);
    	}
    	if(event.getSource() == myShelves) {
    		browseBox.setVisible(false);
    		topBooksBox.setVisible(false);
    		categoriesBox.setVisible(false);
    		readingBox.setVisible(false);
    		savedBox.setVisible(false);
    		myShelvesBox.setVisible(true);
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recentlyAdded = new ArrayList<>(recentlyAdded());
		recommended = new ArrayList<>(books());
		int column = 0;
		int row = 1;
		
		
		try {
			for(Book value : recentlyAdded) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("card.fxml"));
				HBox cardBox = loader.load();
				CardController cardController = loader.getController();
				cardController.setData(value);
				cardLayout.getChildren().add(cardBox);
			}
			for(Book book : recommended) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("book.fxml"));
				VBox bookBox = loader.load();
				BookController bookController = loader.getController();
				bookController.setData(book);
				
				if(column == 6) {
					column=0;
					++row;
				}
				
				bookContainer.add(bookBox, column++, row);
				GridPane.setMargin(bookBox, new Insets(10));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		return ls;
	}
	
	public void switchtoUserInformation(MouseEvent e) throws IOException {
		if(e.getSource() == user) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserScene.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root, 1515, 770);
			stage.setTitle("Goodreads");
			stage.setScene(scene);
			stage.show();
		}
	}
}
