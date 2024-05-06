package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Follow;

public class DAOFollow implements DAOInterface<Follow>{

	public static DAOFollow getInstance() {
		return new DAOFollow();
	}
	
	@Override
	public void insert(Follow follow) {
		String query = "INSERT INTO follower (follower_id, user_id) VALUES (?, ?)";
        String query2 = "INSERT INTO following (user_id, following_id) VALUES (?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(query2)) {
            stmt1.setInt(1, follow.getFollowerId());
            stmt1.setLong(2, follow.getUserId());
            int result1 = stmt1.executeUpdate();

            stmt2.setLong(1, follow.getFollowerId());
            stmt2.setInt(2, follow.getFollowingId());
            int result2 = stmt2.executeUpdate();
            
            if (result1 > 0 && result2 > 0) {
                JOptionPane.showMessageDialog(null, "Follow operation successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Follow operation failed!");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


	@Override
	public void update(Follow t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Follow follow) {
		String query = "DELETE FROM follower WHERE follower_id = ? AND user_id = ?";
        String query2 = "DELETE FROM following WHERE user_id = ? AND following_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(query2)) {
            stmt1.setInt(1, follow.getFollowerId());
            stmt1.setLong(2, follow.getUserId());
            int result1 = stmt1.executeUpdate();

            stmt2.setLong(1, follow.getFollowerId());
            stmt2.setInt(2, follow.getFollowingId());
            int result2 = stmt2.executeUpdate();
            
            if (result1 > 0 && result2 > 0) {
                JOptionPane.showMessageDialog(null, "Unfollow operation successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Unfollow operation failed!");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}

	@Override
	public ArrayList<Follow> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Follow selectByID(Follow t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Follow> selectByCondition(String condition) {
	    ArrayList<Follow> followers = new ArrayList<>();
	    String query = "SELECT follower_id FROM follower WHERE user_id = ?";
	    try (Connection conn = JDBCUtil.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, condition); // Truyền user_id vào câu lệnh SQL
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Follow follower = new Follow();
	                follower.setFollowerId(rs.getInt("follower_id"));
	                followers.add(follower);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return followers;
	}
	public ArrayList<Follow> selectByUserId(String condition) {
        ArrayList<Follow> followingIds = new ArrayList<>();
        String query = "SELECT following_id FROM following WHERE user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, condition);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	Follow following = new Follow();
	                following.setFollowingId(rs.getInt("following_id"));
                    followingIds.add(following);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return followingIds;
    }
	public int countFollowersByUserID(long userID) {
        int followerCount = 0;
        String query = "SELECT COUNT(*) FROM follower WHERE user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    followerCount = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return followerCount;
    }
	public int countFollowingByUserID(long userID) {
        int followingCount = 0;
        String query = "SELECT COUNT(*) FROM following WHERE user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    followingCount = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return followingCount;
    }
	public boolean checkIfFollowed(long followerId, long userId) {
	    String query = "SELECT * FROM follower WHERE follower_id = ? AND user_id = ?";
	    try (Connection conn = JDBCUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setLong(1, followerId);
	        stmt.setLong(2, userId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next(); // Trả về true nếu có kết quả, tức là đã follow, ngược lại trả về false
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}

}
