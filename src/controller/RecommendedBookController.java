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
import database.DAOGenre;
import database.DAOShelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Book;
import model.Genre;
import model.Shelf;
import model.User;

public class RecommendedBookController implements Initializable {

    @FXML
    private Label authorName;

    @FXML
    private Label averageRating;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;
    
    @FXML
    private GridPane genreContainer;
    
    public static Book currentBook;
    @FXML
    private MyShelvesPageController myShelvesPageController;
    private HomePageController homePageController; 
	private List<Genre> allGenres = new ArrayList<>();
    private List<Genre> listGenres;
    
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

    @FXML
    void onClickAddShelfDefault(ActionEvent event) {
			Shelf shelf = new Shelf();
    		shelf.setShelfName("Want-to-read");
    		shelf.setBookID(currentBook.getBookID());
    		DAOShelf.getInstance().insert(shelf);
    		if(myShelvesPageController!=null) {
            	myShelvesPageController.reloadDataAndRefreshUI();
    		}
    		if(homePageController!=null) {
    			homePageController.reloadDataAndRefreshUI();
    		}
    }
    
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		homePageController = HomePageController.getInstance();
		currentBook = DAOBook.getInstance().getRandomBook(User.getInstance().getUserId());
		if(currentBook!= null) {
		bookName.setText(currentBook.getName());
        authorName.setText(currentBook.getAuthor());
        float averageRatingValue = currentBook.getAverageRating();
        String formattedRating = String.format("%.1f", averageRatingValue);
        averageRating.setText(formattedRating);
        
        Blob imageBlob = currentBook.getImageBook();
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
        listGenres = new ArrayList<>(getAllGenresFromDatabase());
        allGenres.addAll(listGenres);
        showGenres();
		}
		else {
			// Đóng cửa sổ và dừng luồng
            HomePageController.recommendedBookThread.closeCurrentStage();
            HomePageController.recommendedBookThread.interrupt();
		}
	}
    
}