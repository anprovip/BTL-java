package database;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import model.Book;
import model.User;

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
			st.setString(2,t.getName());
			st.setLong(3,t.getPublishDate());
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
			String sql ="select * from book join book_author on book.isbn=book_author.isbn join author on book_author.author_id=author.author_id";
;
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
			    Book book = new Book();
			    book.setName(rs.getString("book_title"));
			    book.setAuthor(rs.getString("author_name"));
			    book.setBookID(rs.getString("isbn"));
			    book.setAverageRating(rs.getFloat("average_rating"));
			    // Đọc dữ liệu ảnh từ cột "book_image"
			    Blob imageBlob = rs.getBlob("book_image");
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        book.setImageBook(new SerialBlob(imageData));
			    }
			    
			    listBook.add(book);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listBook;
	}

	@Override
	public Book selectByID(Book book) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM book JOIN book_author ON book.isbn = book_author.isbn " +
	                     "JOIN author ON book_author.author_id = author.author_id " +
	                     "WHERE book.isbn = ?";

	        PreparedStatement st = connection.prepareStatement(sql);
	        st.setString(1, book.getBookID());
	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {
	            book.setName(rs.getString("book_title"));
	            book.setAuthor(rs.getString("author_name"));
	            book.setBookID(rs.getString("isbn"));
	            book.setAverageRating(rs.getFloat("average_rating"));
	            // Đọc dữ liệu ảnh từ cột "book_image"
	            Blob imageBlob = rs.getBlob("book_image");
	            if (imageBlob != null) {
	                // Chuyển đổi Blob thành mảng byte
	                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
	                // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
	                book.setImageBook(new SerialBlob(imageData));
	            }
	        }
	        JDBCUtil.closeConnection(connection);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return book;
	}
	

	@Override
	public ArrayList<Book> selectByCondition(String condition) {
		ArrayList<Book> listBook = new ArrayList<Book>();
		String searchPattern = "%" + condition + "%";
		try {
			Connection connection = JDBCUtil.getConnection();
			 String sql = "SELECT * FROM book "
	                    + "JOIN book_author ON book.isbn = book_author.isbn "
	                    + "JOIN author ON book_author.author_id = author.author_id "
	                    + "WHERE book.isbn LIKE ? OR book.book_title LIKE ? OR author.author_name LIKE ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, searchPattern);
	            statement.setString(2, searchPattern);
	            statement.setString(3, searchPattern);
	            
	            ResultSet rs = statement.executeQuery();
			while(rs.next()) {
			    Book book = new Book();
			    book.setName(rs.getString("book_title"));
			    book.setAuthor(rs.getString("author_name"));
			    book.setBookID(rs.getString("isbn"));
			    // Đọc dữ liệu ảnh từ cột "book_image"
			    Blob imageBlob = rs.getBlob("book_image");
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        book.setImageBook(new SerialBlob(imageData));
			    }
			    
			    listBook.add(book);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listBook;
		
	}
	public ArrayList<Book> selectByShelfName(String condition1, long condition2) {
		ArrayList<Book> listBook = new ArrayList<Book>();
		
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "SELECT book.*, shelf_name, author_name FROM book " +
		             "JOIN book_author ON book.isbn = book_author.isbn " +
		             "JOIN author ON book_author.author_id = author.author_id " +
		             "JOIN shelf ON shelf.isbn = book.isbn " + // Thêm khoảng trắng ở đây
		             "WHERE shelf.shelf_name = ? AND shelf.user_id = ?";

	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, condition1);
	            statement.setLong(2, condition2);
	            
	            ResultSet rs = statement.executeQuery();
	            while(rs.next()) {
			    Book book = new Book();
			    book.setName(rs.getString("book_title"));
			    book.setAuthor(rs.getString("author_name"));
			    book.setBookID(rs.getString("isbn"));
			    book.setAverageRating(rs.getFloat("average_rating"));
			    book.setShelfName(rs.getString("shelf_name"));
			    // Đọc dữ liệu ảnh từ cột "book_image"
			    Blob imageBlob = rs.getBlob("book_image");
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        book.setImageBook(new SerialBlob(imageData));
			    }
			    
			    listBook.add(book);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listBook;
		
	}
	
	
}
