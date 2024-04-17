package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            String sql = "INSERT INTO user(username, password, email, phoneNumber) VALUES(?, SHA2(?, 256), ?, ?)";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
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
        ArrayList<User> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user";
            statement = connect.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                User user = new User(
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("email"),
                    result.getString("phoneNumber")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User selectByID(User user) {
        // Viết code xử lý lấy user theo ID nếu cần
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
                user.setUsername(username1);
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
                user = new User(
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("email"),
                    result.getString("phoneNumber")
                );
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
    public boolean updateUserInfo(User user, String oldUsername) {
        try {
            String sql = "UPDATE user SET username = ?, password = SHA2(?, 256), email = ?, phoneNumber = ? WHERE username = ?";
            statement = connect.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, oldUsername); // Thêm username cũ vào để xác định dòng cần cập nhật
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}	
