package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOAuthor;
import database.DAOBook;
import database.DAOFollow;
import database.DAOShelf;
import database.DAOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import model.User;

public class AuthorController implements Initializable {
    @FXML
    private BorderPane AuthorBorderPane;

    @FXML
    private TextArea authorDetailsInformation;

    @FXML
    private Label authorName;

    @FXML
    private VBox boxToHide;

    @FXML
    private Button createButton;

    @FXML
    private HBox home;

    @FXML
    private ImageView imageAuthor;

    @FXML
    private HBox myShelves;

    @FXML
    private VBox resizeVBox;

    @FXML
    private HBox search;

    @FXML
    private HBox titleToHide;

    @FXML
    private HBox user;
    
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    public Author currentAuthor;
    
    @FXML
    private Label authorBook;
    
    @FXML
    private GridPane bookContainer;
    
    private List<Book> listBook;
    private List<Book> allBooks = new ArrayList<>();
    
    private List<Book> getAllBooksFromDatabase() {
    	return DAOBook.getInstance().selectByAuthor(currentAuthor.getAuthorName());
    }
    
    public void setData(Author author) {
    	authorName.setText(author.getAuthorName());
    	authorBook.setText(author.getAuthorName()+"'s Books");
    	currentAuthor = DAOAuthor.getInstance().selectByName(authorName.getText());
    	System.out.println(currentAuthor.getAuthorInfo());
    	authorDetailsInformation.setText(currentAuthor.getAuthorInfo());
        if (currentAuthor != null) {
            Blob imageBlob = currentAuthor.getImageAuthor();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imageAuthor.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
        } else {
            // Xử lý khi không tìm thấy user
        }
        listBook = new ArrayList<>(getAllBooksFromDatabase());
        allBooks.addAll(listBook);
        showBooks();
        
    }
    public void showBooks() {
        int column = 0;
        int row = 1;
        for (int i = 0; i < allBooks.size(); i++) {
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
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void onClickHome(MouseEvent event) throws IOException {
    	new ChangeScene(AuthorBorderPane, "/views/HomePageScene.fxml");
    	
    }

    @FXML
    void onClickSearch(MouseEvent event) throws IOException {
    	new ChangeScene(AuthorBorderPane, "/views/SearchPageScene.fxml");
    }

    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	new ChangeScene(AuthorBorderPane, "/views/UserScene.fxml");
    }
    @FXML
    public void switchtoMyShelves(MouseEvent e) throws IOException {
		if(e.getSource() == myShelves) {
			new ChangeScene(AuthorBorderPane, "/views/MyShelvesPageScene.fxml");
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
            if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myShelvesPageController = MyShelvesPageController.getInstance();
		
	}
}
