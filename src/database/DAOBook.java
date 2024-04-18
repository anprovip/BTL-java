package database;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Book;

public class DAOBook implements DAOInterface<Book> {
	
	public static DAOBook getInstance() {
		return new DAOBook();
	}

	@Override
	public void update(Book t) {
		try {
		Connection connection = JDBCUtil.getConnection();
		//b2
		String sql = "INSERT INTO book(isbn ,book_title ,publication_year)"
				+ "VALUES(?,?,?)";
		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, t.getBookID());
			st.setString(1,t.getName());
			st.setLong(1,t.getPublishDate());
			st.executeUpdate();
			
		JDBCUtil.closeConnection(connection);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void insert(Book t) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        PreparedStatement checkAuthorStatement = connection.prepareStatement("SELECT author_id FROM author WHERE author_name = ?");
	        checkAuthorStatement.setString(1, t.getAuthor());

	        ResultSet resultSet = checkAuthorStatement.executeQuery();

	        if (resultSet.next()) {
	            // Tác giả đã tồn tại, lấy ID của tác giả từ kết quả
	            int authorId = resultSet.getInt("author_id");

	            // Thêm thông tin sách và liên kết với tác giả vào cơ sở dữ liệu
	            PreparedStatement insertBookStatement = connection.prepareStatement("INSERT INTO book(isbn ,book_title ,publication_year, book_image) VALUES(?,?,?,?)");
	            insertBookStatement.setString(1, t.getBookID());
	            insertBookStatement.setString(2, t.getName());
	            insertBookStatement.setLong(3, t.getPublishDate());
	            InputStream inputStream = new FileInputStream(new File(t.getImageSrc()));
	            insertBookStatement.setBlob(4, inputStream);
	            insertBookStatement.executeUpdate();

	            // Liên kết sách và tác giả trong bảng trung gian Sách_TácGiả
	            PreparedStatement linkBookAuthorStatement = connection.prepareStatement("INSERT INTO book_author (isbn, author_id) VALUES (?, ?)");
	            linkBookAuthorStatement.setString(1, t.getBookID());
	            linkBookAuthorStatement.setLong(2, authorId);
	            linkBookAuthorStatement.executeUpdate();
	        } else {
	            // Tác giả chưa tồn tại, thêm tác giả mới vào cơ sở dữ liệu và sau đó thêm thông tin sách và liên kết với tác giả mới
	            PreparedStatement insertAuthorStatement = connection.prepareStatement("INSERT INTO author (author_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	            insertAuthorStatement.setString(1, t.getAuthor());
	            insertAuthorStatement.executeUpdate();

	            ResultSet generatedKeys = insertAuthorStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int authorId = generatedKeys.getInt(1);

	                // Thêm thông tin sách và liên kết với tác giả vào cơ sở dữ liệu
	                PreparedStatement insertBookStatement = connection.prepareStatement("INSERT INTO book(isbn ,book_title ,publication_year, book_image) VALUES(?,?,?,?)");
	                insertBookStatement.setString(1, t.getBookID());
	                insertBookStatement.setString(2, t.getName());
	                insertBookStatement.setLong(3, t.getPublishDate());
	                InputStream inputStream = new FileInputStream(new File(t.getImageSrc()));
	                insertBookStatement.setBlob(4, inputStream);
	                insertBookStatement.executeUpdate();

	                // Liên kết sách và tác giả trong bảng trung gian Sách_TácGiả
	                PreparedStatement linkBookAuthorStatement = connection.prepareStatement("INSERT INTO book_author (isbn, author_id) VALUES (?, ?)");
	                linkBookAuthorStatement.setString(1, t.getBookID());
	                linkBookAuthorStatement.setLong(2, authorId);
	                linkBookAuthorStatement.executeUpdate();
	            }
	        }

	        JDBCUtil.closeConnection(connection);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void delete(Book t) {
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "DELETE FROM book WHERE isbn =?;";
			
				PreparedStatement st = connection.prepareStatement(sql);
				st.setString(1, t.getBookID());
				st.executeUpdate();
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public ArrayList<Book> selectAll() {
		ArrayList<Book> listBook = new ArrayList<Book>();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "SELECT * FROM BOOK";
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listBook;
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
