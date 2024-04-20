package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Review;

public class DAOReview implements DAOInterface<Review>{
	public static DAOReview getInstance() {
		return new DAOReview();
	}

	@Override
	public void insert(Review review) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "INSERT INTO review (isbn, user_id, review_text, rating, review_date) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, review.getISBN());
	        statement.setLong(2, review.getUserId()); // Thêm userId vào câu lệnh SQL
	        statement.setString(3, review.getReviewText());
	        statement.setInt(4, review.getRating());
	        statement.setDate(5, review.getReviewDate());
	        statement.executeUpdate();
	        JDBCUtil.closeConnection(connection);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review selectByID(Review t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Review> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}


}
