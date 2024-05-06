package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DAOFollow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Follow;

public class FollowingPopupController implements Initializable{
    @FXML
    private ScrollPane followingPopupScrollPane;

    @FXML
    private GridPane listFollowingContainer;
    private Follow currentFollow;
    private List<Follow> allFollowingUserHave = new ArrayList<>();
    private List<Follow> recentlyFollowing;

    private List<Follow> getAllFollowingFromDatabase(Follow follow) {
		return DAOFollow.getInstance().selectByUserId(Long.toString(follow.getUserId()));
		
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//showFollowing();
	}
    public void setData(Follow follow) {
    	currentFollow = follow;
    	recentlyFollowing = new ArrayList<>(getAllFollowingFromDatabase(currentFollow));
    	allFollowingUserHave.addAll(recentlyFollowing);
    	showFollowing();
    }
    
    public void showFollowing() {
    	
        System.out.println("Co den day khong");
        int column = 0;
        int row = 1;
        for (int i = 0; i < allFollowingUserHave.size(); i++) {
            Follow follow = allFollowingUserHave.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FollowCard.fxml"));
            
            try {
            	HBox followPane = loader.load();
            	FollowController followController = loader.getController();
            	followController.setDataForFollowing(follow);
            	listFollowingContainer.add(followPane, column, row++);
                GridPane.setMargin(followPane, new Insets(10));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
