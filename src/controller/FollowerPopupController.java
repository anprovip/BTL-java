package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOFollow;
import database.DAOShelf;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Follow;
import model.Shelf;
import model.User;

public class FollowerPopupController implements Initializable {
    @FXML
    private ScrollPane followerPopupScrollPane;

    @FXML
    private GridPane listFollowerContainer;

    private List<Follow> allFollowerUserHave = new ArrayList<>();
    private List<Follow> recentlyFollower;

    private Follow currentFollow;
    
    private List<Follow> getAllFollowersFromDatabase(Follow follow) {
		return DAOFollow.getInstance().selectByCondition(Long.toString(follow.getUserId()));
		
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	/*System.out.println(" follow co user id nay nay "+currentFollow.getUserId());
    	recentlyFollower = new ArrayList<>(getAllFollowersFromDatabase(currentFollow));
    	allFollowerUserHave.addAll(recentlyFollower);
    	showShelves();
	    */
	}
    public void setData(Follow follow) {
    	currentFollow = follow;
    	recentlyFollower = new ArrayList<>(getAllFollowersFromDatabase(currentFollow));
    	allFollowerUserHave.addAll(recentlyFollower);
    	showFollowers();
    }
    
    public void showFollowers() {
    	
        System.out.println("Co den day khong");
        int column = 0;
        int row = 1;
        for (int i = 0; i < allFollowerUserHave.size(); i++) {
            Follow follow = allFollowerUserHave.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FollowCard.fxml"));
            
            try {
            	HBox followPane = loader.load();
            	FollowController followController = loader.getController();
            	followController.setDataForFollower(follow);
            	listFollowerContainer.add(followPane, column, row++);
                GridPane.setMargin(followPane, new Insets(10));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
