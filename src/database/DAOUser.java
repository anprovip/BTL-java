package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import model.User;

public class DAOUser implements DAOInterface<User> {
    
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;
    User user = User.getInstance();
    
    public DAOUser() {
        connect = JDBCUtil.getConnection();
    }
    public static DAOUser getInstance() {
        return new DAOUser();
    }
    @Override
    public void insert(User user) {
        try {
            String sql = "INSERT INTO user(username, password, email, phoneNumber, user_image) VALUES(?, SHA2(?, 256), ?, ?, ?)";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            if (user.getImageSrc() != null && new File(user.getImageSrc()).exists()) {
                InputStream inputStream = new FileInputStream(new File(user.getImageSrc()));
                statement.setBlob(5, inputStream);
            } else {
                // If user.getImageSrc() is null or points to an invalid file, use default image
                String defaultImagePath = "/img/banana.png"; // Replace this with the path to your default image
                InputStream defaultInputStream = getClass().getResourceAsStream(defaultImagePath);
                statement.setBlob(5, defaultInputStream);
            }
            statement.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        // Viết code xử lý update thông tin user nếu cần
    }

    @Override
    public void delete(User user) {
        // Viết code xử lý xóa user nếu cần
    }

    @Override
    public ArrayList<User> selectAll() {
        List<User> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user";
            statement = connect.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setEmail(result.getString("email"));
                user.setPhoneNumber(result.getString("phoneNumber"));
                user.setPassword(result.getString("password"));
                user.setUsername(result.getString("username"));
                Blob imageBlob = result.getBlob("user_image");
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        user.setImageUser(new SerialBlob(imageData));
			    }
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<User>) userList;
    }

    @Override
    public User selectByID(User user) {
        
        return null;
    }

    @Override
    public ArrayList<User> selectByCondition(String condition) {
        // Viết code xử lý lấy user theo điều kiện nếu cần
        return null;
    }
    
    public boolean selectByUsernameAndPassword(String username, String password) {
        
        try {
            String sql = "SELECT * FROM user WHERE username = ? AND password = SHA2(?, 256)";
            statement = connect.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            result = statement.executeQuery();
            if (result.next()) {
            	String username1 = result.getString("username");
                String password1 = result.getString("password");
                String email = result.getString("email");

                String phoneNumber = result.getString("phoneNumber");
                Long userId = result.getLong("user_id");
                

                user.setUsername(username1);
                user.setUserId(userId);
                user.setPassword(password);
                user.setPhoneNumber(phoneNumber);
                user.setEmail(email);
                Blob imageBlob = result.getBlob("user_image");
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        user.setImageUser(new SerialBlob(imageData));
			    }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkUsernameExist(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, username);
            result = statement.executeQuery();
            if(result.next()) {
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		return false;
    }
    public User selectByUsername(String username) {
    	
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, username);
            result = statement.executeQuery();
            if (result.next()) {
                user = new User();
                user.setEmail(result.getString("email"));
                user.setPhoneNumber(result.getString("phoneNumber"));
                user.setPassword(result.getString("password"));
                user.setUsername(result.getString("username"));
                Blob imageBlob = result.getBlob("user_image");
                user.setUserId(result.getLong("user_id"));
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính userImage của đối tượng User
			        user.setImageUser(new SerialBlob(imageData));
			    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    
    public boolean updateUserInfo(User user) {
        try {
            String sql = "UPDATE user SET  email = ?, phoneNumber = ?, user_image = ?,display_name= ? WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPhoneNumber());
            
            if (user.getImageSrc() != null) {

                // Tạo inputStream từ file ảnh
                InputStream inputStream = new FileInputStream(new File(user.getImageSrc()));
                statement.setBlob(3, inputStream);

                // Tạo mảng byte từ file ảnh
                byte[] imageData = Files.readAllBytes(Paths.get(user.getImageSrc()));
                statement.setBytes(3, imageData);
            } else {
                // Nếu không có ảnh được chọn, truyền null vào cột user_image
                statement.setBytes(3, null);

            }
            statement.setString(4, user.getDisplayName());
            statement.setString(5, user.getUsername()); // Thêm username cũ vào để xác định dòng cần cập nhật
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean changePassword(String username, String newPassword) {
        // Kiểm tra xem email và số điện thoại có khớp với thông tin trong database không
        
            try {
                // Tạo đối tượng User mới với mật khẩu mới
                User updatedUser = new User();
                updatedUser.setUsername(username);
                updatedUser.setPassword(newPassword);
                
                // Gọi phương thức updatePassword từ DAOUser để cập nhật mật khẩu mới vào database
                boolean success = updatePassword(updatedUser);
                
                return success;
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi nếu có
                return false;
            }
       
    }


    // Phương thức để cập nhật mật khẩu mới vào database
    private boolean updatePassword(User user) {
        try {
            String sql = "UPDATE user SET password = SHA2(?, 256) WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUsername());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}	
