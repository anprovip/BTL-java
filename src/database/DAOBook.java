package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.PreparableStatement;

import model.Book;

public class DAOBook implements DAOInterface<Book> {
	
	public static DAOBook getInstance() {
		return new DAOBook();
	}

	@Override
	public void insert(Book t) {
		try {
		Connection connection = JDBCUtil.getConnection();
		//b2
		String sql = "INSERT INTO book(isbn ,book_title ,publication_year)"
				+ "VALUES(?,?,?)";
		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, t.getBookID());
			st.setString(1,t.getName());
			st.setLong(1,t.getPublishDate());
			st.executeUpdate();
			
		JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Book t) {
		try {
		Connection connection = JDBCUtil.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Book t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Book> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book selectByID(Book t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Book> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}