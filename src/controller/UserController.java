package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import database.DAOBook;
import database.DAOGenre;
import database.DAOUser;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import model.ChangeScene;
import model.User;
import model.Genre;

public class UserController implements Initializable{
	
	private Stage stage;
	
    @FXML
    private VBox addBookBox;

    @FXML
    private Button addCover;

    @FXML
    private HBox backBox;

    @FXML
    private TextField bookTitle;

    @FXML
    private ImageView coverImage;

    @FXML
    private TextField displayName;
    
    @FXML
    private Label displayNameLable;

    @FXML
    private Button editButton;

    @FXML
    private TextField emailInfo;

    @FXML
    private ImageView imageInfo;

    @FXML
    private TextField isbn;
    
    @FXML
    private Label selection;

    @FXML
    private ListView<String> listView;

    @FXML
    private HBox logoutBox;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField oldPassword;

    @FXML
    private HBox password;

    @FXML
    private VBox passwordBox;
    
    @FXML
    private TextField author;

    @FXML
    private TextField phoneInfo;

    @FXML
    private HBox profile;

    @FXML
    private VBox profileBox;

    @FXML
    private TextField pubYear;

    @FXML
    private TextField reenterPasswordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveButton1;

    @FXML
    private TextArea summary;

    @FXML
    private BorderPane userBorderPane;

    private LoginController loginController;
    
    private static UserController instance;
    
    public static UserController getInstance() {
        return instance;
    }
    
    DAOUser daoUser = DAOUser.getInstance();
    User user = User.getInstance();
    @FXML
    private MyShelvesPageController myShelvesPageController;
    
    public void switchToHome(MouseEvent e) throws IOException {
    	if(e.getSource() == backBox) {
    		new ChangeScene(userBorderPane, "/views/HomePageScene.fxml");
    		
    	}
    }
    public void switchToLogin(MouseEvent e) throws IOException {
        if(e.getSource() == logoutBox) {
            
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION) {
                // Làm rỗng HashMap trước khi chuyển scene
                ChangeScene.clearScenes();
                
                new ChangeScene(userBorderPane, "/views/LoginScene.fxml");
            }
        }
    }

    @FXML
    private void editImage(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        
        // Set filter cho file chooser để chỉ chấp nhận các file ảnh
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Mở hộp thoại chọn tập tin
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                // Chuyển đổi đường dẫn file thành URL
                String localUrl = file.toURI().toURL().toString();
                
                // Tạo ảnh mới từ URL và cập nhật imageInfo
                Image image = new Image(localUrl);
                imageInfo.setImage(image);
                
                // Lấy đường dẫn tuyệt đối của file
                String absolutePath = file.getAbsolutePath();
                
                user.setImageSrc(absolutePath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu không thể chuyển đổi đường dẫn thành URL
            }
        }
    }

    
    public void getUserInfo(String currentUsername) {
        User user = daoUser.selectByUsername(currentUsername);
        	displayNameLable.setText(user.getDisplayName());
        	System.out.println(user.getDisplayName()+" 1");
        if (user != null) {
            emailInfo.setText(user.getEmail());
            phoneInfo.setText(user.getPhoneNumber());
            displayName.setText(user.getDisplayName());
            Blob imageBlob = user.getImageUser();
            if (imageBlob != null) {
                try {
                    // Chuyển đổi Blob thành mảng byte
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    // Tạo đối tượng Image từ mảng byte và hiển thị trong ImageView
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    imageInfo.setImage(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            
            System.out.println("User not found!");
        }
    }
    
    @FXML
    public void saveUserInfo(ActionEvent event) {
        // Lấy thông tin từ các TextField
        String dName = displayName.getText();
        String email = emailInfo.getText();
        String phoneNumber = phoneInfo.getText();
        String absolutePath = user.getImageSrc();
            // Tạo đối tượng User mới với thông tin được điền mới
            User updatedUser = new User();
            updatedUser.setDisplayName(dName);
            updatedUser.setEmail(email);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setUsername(user.getUsername());
            updatedUser.setImageSrc(absolutePath);
            // Gọi phương thức updateUserInfo từ DAOUser để cập nhật thông tin trong cơ sở dữ liệu
            boolean success = daoUser.updateUserInfo(updatedUser);
            
            if (success) {
                // Thông báo cho người dùng rằng thông tin đã được cập nhật thành công
                JOptionPane.showMessageDialog(null, "User information updated successfully!");
            } else {
                // Thông báo lỗi nếu không thể cập nhật thông tin
                JOptionPane.showMessageDialog(null, "Failed to update user information!");
            }
        }
    
    @FXML
    private void savePassword(ActionEvent event) {
        // Lấy thông tin từ các TextField
        String oldPass = oldPassword.getText();
        if(!oldPass.equals(user.getPassword())) {
        	JOptionPane.showMessageDialog(null, "Your old password is wrong!");
            return;
        }
        String newPassword = newPasswordField.getText(); // Sử dụng newPasswordField thay vì newPassword
        String reenteredPassword = reenterPasswordField.getText(); // Sử dụng reenterPasswordField thay vì reenterPassword
        
        // Kiểm tra xem mật khẩu mới và mật khẩu nhập lại có khớp nhau không
        if (!newPassword.equals(reenteredPassword)) {
            // Thông báo lỗi nếu mật khẩu nhập lại không trùng khớp với mật khẩu mới
            JOptionPane.showMessageDialog(null, "New password and re-entered password do not match!");
            return; // Kết thúc phương thức nếu có lỗi
        }
        
        // Gọi hàm changePassword từ DAOUser để thay đổi mật khẩu
        boolean success = daoUser.changePassword(user.getUsername(), newPassword);
        
        if(success) {
        	JOptionPane.showMessageDialog(null, "Update password successfully!");
        }
    }


    @FXML
    private void switchToProfile(MouseEvent event) {
        profileBox.setVisible(true);
        passwordBox.setVisible(false);
        addBookBox.setVisible(false);
    	
    }

    @FXML
    private void switchToPassword(MouseEvent event) {
        profileBox.setVisible(false);
        passwordBox.setVisible(true);
        addBookBox.setVisible(false);
    }
    
    @FXML
    void switchToAddBook(MouseEvent event) {
    	profileBox.setVisible(false);
        passwordBox.setVisible(false);
        addBookBox.setVisible(true);
    }
    
 /*   public void reloadDataAndRefreshUI() {
        // Lấy lại thông tin người dùng từ cơ sở dữ liệu
    	
        String currentUsername = user.getUsername();
        getUserInfo(currentUsername);
        
        // Cập nhật thông tin người dùng trên giao diện
        emailInfo.setText(user.getEmail());
        phoneInfo.setText(user.getPhoneNumber());
        oldPassword.setText("");
        newPasswordField.setText("");
        reenterPasswordField.setText("");
        System.out.println("User data reloaded and UI refreshed.");
    } */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String currentUsername = User.getInstance().getUsername();
		getUserInfo(currentUsername);
		
		myShelvesPageController = MyShelvesPageController.getInstance();
		displayName.setText(displayName.getText());
		instance = this;
		ArrayList<Genre> allGenres = DAOGenre.getInstance().selectAll();
		
		for (Genre genre : allGenres) {
	        listView.getItems().add(genre.getGenreName());
	    }
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
	}
	
	private void selectionChanged(ObservableValue<? extends String> Observable,String oldVal, String newVal) {
		ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
		String getSelectedItem = (selectedItems.isEmpty())?"Nothing selected":selectedItems.toString();
		selection.setText(getSelectedItem);
		
	}
	
    @FXML
    void addBookCover(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        
        // Set filter cho file chooser để chỉ chấp nhận các file ảnh
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Mở hộp thoại chọn tập tin
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                String localUrl = file.toURI().toURL().toString();
                
                Image image = new Image(localUrl);
                coverImage.setImage(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu không thể chuyển đổi đường dẫn thành URL
            }
        }
    }
    
    @FXML
    void onClickAddBook(ActionEvent event) {
        // Lấy thông tin về sách từ các trường nhập liệu
        String title = bookTitle.getText();
        String authorName = author.getText();
        String bookId = isbn.getText();
        String yearText = pubYear.getText();
        String bookSummary = summary.getText();
        
        if (title.isEmpty() || authorName.isEmpty() || bookId.isEmpty() || yearText.isEmpty() || bookSummary.isEmpty()) {
            // Thông báo cho người dùng nhập đầy đủ thông tin
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }
        
        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            // Thông báo cho người dùng nhập đúng định dạng năm
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Năm xuất bản phải là số.");
            alert.showAndWait();
            return;
        }
        
        // Lấy danh sách thể loại được chọn từ ListView
        ObservableList<String> selectedGenres = listView.getSelectionModel().getSelectedItems();
        
        if (selectedGenres.isEmpty()) {
            // Thông báo cho người dùng chọn ít nhất một thể loại
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ít nhất một thể loại.");
            alert.showAndWait();
            return;
        }
        
        // Chuyển đổi hình ảnh thành byte array
        Image image = coverImage.getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageData = outputStream.toByteArray();
        Blob imageBlob = null;
        try {
            imageBlob = new javax.sql.rowset.serial.SerialBlob(imageData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Chuyển đổi tên thể loại thành ID thể loại
        ArrayList<Genre> genres = new ArrayList<>();
        for (String genreName : selectedGenres) {
            genres.add(DAOGenre.getInstance().selectByName(genreName));
        }
        
        // Thêm sách vào cơ sở dữ liệu
        Book book = new Book();
        book.setName(title);
        book.setAuthor(authorName);
        book.setBookID(bookId);
        book.setPublishDate(year);
        book.setSummary(bookSummary);
        book.setImageBook(imageBlob);
        book.setGenresOfBook(genres);
        
        DAOBook.getInstance().insert(book);
    }

}
