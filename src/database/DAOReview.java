package database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import model.Book;
import model.Review;
import model.User;

public class DAOReview implements DAOInterface<Review>{
	public static DAOReview getInstance() {
		return new DAOReview();
	}

	@Override
	public void insert(Review review) {
		 try (Connection connection = JDBCUtil.getConnection()) {
		        String sql = "INSERT INTO Review (review_text, rating, user_id, isbn) VALUES (?,?,?,?)";
		        PreparedStatement statement = connection.prepareStatement(sql);
		        statement.setString(1, review.getReviewText());
		        statement.setFloat(2, review.getRating());
		        statement.setInt(3, review.getUserId());
		        statement.setString(4, review.getISBN());
		        
		        int rowsInserted = statement.executeUpdate();
		        if (rowsInserted > 0) {
		            System.out.println("A new review was inserted successfully!");
		        } else {
		            System.out.println("Failed to insert the review!");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}



	@Override
	public void update(Review t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Review t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Review> selectAll() {
		ArrayList<Review> listReview = new ArrayList<Review>();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql ="select review.*, user.username, user.user_image from review join user on review.user_id=user.user_id join book on review.isbn=book.isbn";
			
			PreparedStatement st = connection.prepareStatement(sql);
			Book book = new Book();
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
			    Review review = new Review();
			    review.setReviewText(rs.getString("review_text"));
			    review.setReviewId(rs.getInt("id"));
			    review.setRating(rs.getFloat("rating"));
			    review.setReviewDate(rs.getDate("review_date"));
			    review.setUserId(rs.getInt("user_id"));
			    review.setISBN(rs.getString("isbn"));
			    User user = new User();
			    user.setUsername(rs.getString("username"));
			    Blob imageBlob = rs.getBlob("user_image");
			    review.setUsername(user.getUsername());
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        review.setUserImage(new SerialBlob(imageData));
			    }
                
			    
			    listReview.add(review);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listReview;
	
	}

	@Override
	public Review selectByID(Review t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Review> selectByCondition(String condition) {
		ArrayList<Review> listReview = new ArrayList<Review>();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql ="select review.*, user.username, user.user_image from review join user on review.user_id=user.user_id where isbn = ?";
			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, condition);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
			    Review review = new Review();
			    review.setReviewText(rs.getString("review_text"));
			    review.setReviewId(rs.getInt("id"));
			    review.setRating(rs.getFloat("rating"));
			    review.setReviewDate(rs.getDate("review_date"));
			    review.setUserId(rs.getInt("user_id"));
			    review.setISBN(rs.getString("isbn"));
			    User user = new User();
			    user.setUsername(rs.getString("username"));
			    Blob imageBlob = rs.getBlob("user_image");
			    review.setUsername(user.getUsername());
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        review.setUserImage(new SerialBlob(imageData));
			    }
                
			    
			    listReview.add(review);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listReview;
	}


}
