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

import database.DAOBook;
import database.DAOFollow;
import database.DAOReview;
import database.DAOShelf;
import database.DAOUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import model.Follow;
import model.Shelf;
import model.User;

public class UserProfileController implements Initializable {
    @FXML
    private Button createButton;

    @FXML
    private HBox home;

    @FXML
    private ImageView imageUser;

    @FXML
    private HBox myShelves;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfFollowers;

    @FXML
    private Label numberOfFollowing;

    @FXML
    private Label numberOfShelves;

    @FXML
    private HBox search;

    @FXML
    private BorderPane UserProfileBorderPane;

    @FXML
    private HBox user;

    @FXML
    private Label userName;
    
    @FXML
    private Button followButton;
    
    @FXML
    private Button unfollowButton;
    
    @FXML
    private MyShelvesPageController myShelvesPageController;

    @FXML
    private VBox boxToHide;

    @FXML
    private HBox titleToHide;
    
    @FXML
    private VBox resizeVBox;
    
    @FXML
    private ImageView avatarOfUser;
    
    @FXML
    private Label displayName;

    @FXML
    private GridPane ShelfContainer;
    
    private List<Shelf> recentlyAddedShelf;
    private List<Shelf> allShelves = new ArrayList<>();
    private List<Node> displayedShelves = new ArrayList<>();
    
    public User currentUserInReview;
    
    private static UserProfileController instance;
    
    public static UserProfileController getInstance() {
        return instance;
    }
    
    public void setData(User user) {
    	userName.setText(user.getUsername());
    	currentUserInReview = DAOUser.getInstance().selectByUsername(user.getUsername());
        if (currentUserInReview != null) {
            Blob imageBlob = currentUserInReview.getImageUser();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imageUser.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            long userID = currentUserInReview.getUserId();
            int shelfCount = DAOShelf.getInstance().countShelvesByUserID(userID);
            int bookCount = DAOShelf.getInstance().getTotalBooksByUserID(userID);
            int followerCount = DAOFollow.getInstance().countFollowersByUserID(userID);
            int followingCount = DAOFollow.getInstance().countFollowingByUserID(userID);
            numberOfShelves.setText(String.valueOf(shelfCount));
            numberOfBooks.setText(String.valueOf(bookCount));
            numberOfFollowers.setText(String.valueOf(followerCount));
            numberOfFollowing.setText(String.valueOf(followingCount));
        } else {
            // Xử lý khi không tìm thấy user
        }
        afterUI();
    }
    
	@FXML
    void onClickHome(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/HomePageScene.fxml");
    	
    }

    @FXML
    void onClickSearch(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/SearchPageScene.fxml");
    }

    @FXML
    void onClickUser(MouseEvent event) throws IOException {
    	new ChangeScene(UserProfileBorderPane, "/views/UserScene.fxml");
    }
    public void switchtoMyShelves(MouseEvent e) throws IOException {
		if(e.getSource() == myShelves) {
			new ChangeScene(UserProfileBorderPane, "/views/MyShelvesPageScene.fxml");
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
    
    private List<Shelf> getAllShelvesFromDatabase(User user) {
		return DAOShelf.getInstance().selectByCondition(Long.toString(user.getUserId()));
		
    }
    
    private void showShelves() {
    	if (ShelfContainer == null) {
            System.err.println("Error: shelfContainer is null.");
            return;
        }
        int column = 0;
        int row = 1;
        for (int i = 0; i < allShelves.size(); i++) {
            Shelf shelf = allShelves.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShelfCard.fxml"));
            
            try {
            	BorderPane shelfPane = loader.load();
                ShelfController shelfController = loader.getController();
                shelfController.setData(shelf);
                //shelfController.getInstance();
                
                shelfController.setDeleteButtonVisible(false);
                shelfController.setDeleteButtonDisable(true);
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                ShelfContainer.add(shelfPane, column++, row);
                GridPane.setMargin(shelfPane, new Insets(25));
                displayedShelves.add(shelfPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	public void afterUI() {
		User user = DAOUser.getInstance().selectByUsername(currentUserInReview.getUsername());
		myShelvesPageController = MyShelvesPageController.getInstance();
		recentlyAddedShelf = new ArrayList<>(getAllShelvesFromDatabase(user));

	    allShelves.addAll(recentlyAddedShelf);
		showShelves();
		long currentUserId = User.getInstance().getUserId();
	    if (DAOFollow.getInstance().checkIfFollowed(currentUserId, currentUserInReview.getUserId())) {
	    	followButton.setVisible(false);
		    followButton.setDisable(true);
		    unfollowButton.setVisible(true);
		    unfollowButton.setDisable(false);
	    }
		//reloadDataAndRefreshUI();
		instance = this;
	}
	public void reloadDataAndRefreshUI() {
	    // Reload dữ liệu từ cơ sở dữ liệu hoặc làm bất kỳ công việc nào cần thiết để cập nhật dữ liệu mới
	    User user = currentUserInReview;
	    allShelves.clear();
	    allShelves.addAll(getAllShelvesFromDatabase(user));
	    
	}
	
	@FXML
	public void followShelf(ActionEvent event) {
		if (checkIfCurrentUserIsReviewUser()) {
	        // Nếu là người dùng đang xem, không thực hiện thêm vào cơ sở dữ liệu
	        return;
	    }
		long currentUserId = User.getInstance().getUserId();
	    if (DAOFollow.getInstance().checkIfFollowed(currentUserId, currentUserInReview.getUserId())) {
	        // Hiển thị thông báo đã follow và không thực hiện thêm vào cơ sở dữ liệu
	        JOptionPane.showMessageDialog(null, "You have followed this user before.");
	    }
	    else {
		    // Thêm userId hiện tại vào follower của shelf.getUserID
		    Follow follow = new Follow();
		    follow.setFollowerId((int)User.getInstance().getUserId());
		    System.out.println(follow.getFollowerId()+" FOLLOWER ID CUA FOLLOW NAY");
		    
		    follow.setUserId(currentUserInReview.getUserId());
		    System.out.println(follow.getUserId()+" USERID CUA FOLLOW NAY");
		    
		    follow.setFollowingId((int)currentUserInReview.getUserId());
		    System.out.println(follow.getFollowingId() + " FOLLOWING ID CUA FOLLOW NAY");
		    DAOFollow.getInstance().insert(follow);
		    
	    }
	    followButton.setVisible(false);
	    followButton.setDisable(true);
	    unfollowButton.setVisible(true);
	    unfollowButton.setDisable(false);
	    
	}

	@FXML
	public void unfollowShelf(ActionEvent event) {
	    // Xóa shelf.getUserID trong following của userId hiện tại
	    Follow follow = new Follow();
	    follow.setFollowerId((int)User.getInstance().getUserId());
	    follow.setUserId((int)currentUserInReview.getUserId());
	    follow.setFollowingId((int)currentUserInReview.getUserId());
	    DAOFollow.getInstance().delete(follow);

	    // Cập nhật giao diện
	    followButton.setVisible(true);
	    followButton.setDisable(false);
	    unfollowButton.setVisible(false);
	    unfollowButton.setDisable(true);
	}
	
	public boolean checkIfCurrentUserIsReviewUser() {
	    long currentUserId = User.getInstance().getUserId();
	    long reviewUserId = currentUserInReview.getUserId();
	    if (currentUserId == reviewUserId) {
	        // Hiển thị thông báo không thể follow chính account của mình
	        JOptionPane.showMessageDialog(null, "You cannot follow your own account.");
	        return true;
	    } else {
	        return false;
	    }
	}
	@FXML
    public void onCLickToFollowers(MouseEvent event) {
		try {
            // Tạo một Stage mới cho cửa sổ popup
			Follow follow = new Follow();
		    follow.setFollowerId((int)User.getInstance().getUserId());
		    System.out.println(follow.getFollowerId()+" FOLLOWER ID CUA FOLLOW NAY");
		    
		    follow.setUserId(currentUserInReview.getUserId());
		    System.out.println(follow.getUserId()+" USERID CUA FOLLOW NAY");
		    
		    follow.setFollowingId((int)currentUserInReview.getUserId());
		    System.out.println(follow.getFollowingId() + " FOLLOWING ID CUA FOLLOW NAY");
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FollowerPopupScene.fxml"));
		    VBox root = loader.load();
		    
		    FollowerPopupController followerPopupController = loader.getController();
		    followerPopupController.setData(follow);
		    
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Load nội dung từ file FXML
            

            // Gán controller cho cửa sổ popup
            //AddShelfPopupController controller = loader.getController();
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCLickToFollowing(MouseEvent event) {
    	try {
    		Follow follow = new Follow();
		    follow.setFollowerId((int)User.getInstance().getUserId());
		    System.out.println(follow.getFollowerId()+" FOLLOWER ID CUA FOLLOW NAY");
		    
		    follow.setUserId(currentUserInReview.getUserId());
		    System.out.println(follow.getUserId()+" USERID CUA FOLLOW NAY");
		    
		    follow.setFollowingId((int)currentUserInReview.getUserId());
		    System.out.println(follow.getFollowingId() + " FOLLOWING ID CUA FOLLOW NAY");
            // Tạo một Stage mới cho cửa sổ popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Load nội dung từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FollowingPopupScene.fxml"));
            VBox root = loader.load();
            FollowingPopupController followingPopupController = loader.getController();
			followingPopupController.setData(follow);
            // Gán controller cho cửa sổ popup
            //AddShelfPopupController controller = loader.getController();
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBoxToHideVisible(boolean visible) {
    	boxToHide.setVisible(visible);
    }

    public void setTitleToHideVisible(boolean visible) {
    	titleToHide.setVisible(visible);
    }
    public void setBoxToHideDisable(boolean disable) {
    	boxToHide.setDisable(disable);
    }

    public void setTitleToHideDisable(boolean disable) {
    	titleToHide.setDisable(disable);
    }
    public void setResizeVBoxPref() {
    	resizeVBox.setPrefWidth(1380);
    	resizeVBox.setPrefHeight(945);
    	resizeVBox.setMaxHeight(945);
    	resizeVBox.setMaxWidth(1380);
    	resizeVBox.setMinHeight(945);
    	resizeVBox.setMinWidth(1380);
    }
    
}
