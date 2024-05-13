package database;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.sql.rowset.serial.SerialBlob;

import model.Book;
import model.Genre;


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
                PreparedStatement insertBookStatement = connection.prepareStatement("INSERT INTO book(isbn ,book_title ,publication_year, book_image, summary) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                insertBookStatement.setString(1, t.getBookID());
                insertBookStatement.setString(2, t.getName());
                insertBookStatement.setLong(3, t.getPublishDate());
                insertBookStatement.setBlob(4,t.getImageBook());
                insertBookStatement.setString(5, t.getSummary());
                insertBookStatement.executeUpdate();

	            // Liên kết sách và tác giả trong bảng trung gian Sách_TácGiả
	            PreparedStatement linkBookAuthorStatement = connection.prepareStatement("INSERT INTO book_author (isbn, author_id) VALUES (?, ?)");
	            linkBookAuthorStatement.setString(1, t.getBookID());
	            linkBookAuthorStatement.setLong(2, authorId);
	            linkBookAuthorStatement.executeUpdate();
	            
	            
	            for (Genre genre : t.getGenresOfBook()) {
	                // Thêm thông tin liên kết giữa sách và thể loại vào bảng book_genre
	                PreparedStatement linkBookGenreStatement = connection.prepareStatement("INSERT INTO book_genre (isbn, genre_id) VALUES (?, ?)");
	                linkBookGenreStatement.setString(1, t.getBookID());
	                linkBookGenreStatement.setInt(2, genre.getGenreID());
	                linkBookGenreStatement.executeUpdate();
	            }
	        } else {
	            // Tác giả chưa tồn tại, thêm tác giả mới vào cơ sở dữ liệu và sau đó thêm thông tin sách và liên kết với tác giả mới
	            PreparedStatement insertAuthorStatement = connection.prepareStatement("INSERT INTO author (author_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	            insertAuthorStatement.setString(1, t.getAuthor());
	            insertAuthorStatement.executeUpdate();

	            ResultSet generatedKeys = insertAuthorStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int authorId = generatedKeys.getInt(1);

	                // Thêm thông tin sách và liên kết với tác giả vào cơ sở dữ liệu
	                PreparedStatement insertBookStatement = connection.prepareStatement("INSERT INTO book(isbn ,book_title ,publication_year, book_image, summary) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	                insertBookStatement.setString(1, t.getBookID());
	                insertBookStatement.setString(2, t.getName());
	                insertBookStatement.setLong(3, t.getPublishDate());
	                insertBookStatement.setBlob(4,t.getImageBook());
	                insertBookStatement.setString(5, t.getSummary());
	                insertBookStatement.executeUpdate();

	                // Liên kết sách và tác giả trong bảng trung gian Sách_TácGiả
	                PreparedStatement linkBookAuthorStatement = connection.prepareStatement("INSERT INTO book_author (isbn, author_id) VALUES (?, ?)");
	                linkBookAuthorStatement.setString(1, t.getBookID());
	                linkBookAuthorStatement.setLong(2, authorId);
	                linkBookAuthorStatement.executeUpdate();
	                
	                for (Genre genre : t.getGenresOfBook()) {
		                // Thêm thông tin liên kết giữa sách và thể loại vào bảng book_genre
		                PreparedStatement linkBookGenreStatement = connection.prepareStatement("INSERT INTO book_genre (isbn, genre_id) VALUES (?, ?)");
		                linkBookGenreStatement.setString(1, t.getBookID());
		                linkBookGenreStatement.setInt(2, genre.getGenreID());
		                linkBookGenreStatement.executeUpdate();
		            }
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
	            book.setSummary(rs.getString("summary"));
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
	
	public ArrayList<Book> selectByGenreCondition(String condition, String genreName) {
		ArrayList<Book> listBook = new ArrayList<Book>();
		String searchPattern = "%" + condition + "%";
		try {
			Connection connection = JDBCUtil.getConnection();
			 String sql = "SELECT * FROM book "
					    + "JOIN book_genre ON book.isbn = book_genre.isbn " 
                        + "JOIN genre ON book_genre.genre_id = genre.genre_id "
	                    + "JOIN book_author ON book.isbn = book_author.isbn "
	                    + "JOIN author ON book_author.author_id = author.author_id "
	                    + "WHERE genre.genre_name = ? AND (book.isbn LIKE ? OR book.book_title LIKE ? OR author.author_name LIKE ?)";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, genreName);
	            statement.setString(2, searchPattern);
	            statement.setString(3, searchPattern);
	            statement.setString(4, searchPattern);
	            ResultSet rs = statement.executeQuery();
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
	public void updateRate(String bookId) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "UPDATE book " +
                    "SET average_rating = (" +
                    "    SELECT CASE " +
                    "        WHEN COUNT(*) = 1 THEN MAX(rating) " + // Nếu chỉ có một đánh giá, lấy giá trị đó
                    "        ELSE AVG(rating) " +
                    "    END " +
                    "    FROM review " +
                    "    WHERE review.isbn = ? " +
                    "    GROUP BY isbn" +
                    ") " +
                    "WHERE isbn = ?";

	        PreparedStatement updateStatement = connection.prepareStatement(sql);
	        updateStatement.setString(1, bookId);
	        updateStatement.setString(2, bookId);
	        updateStatement.executeUpdate();

	        JDBCUtil.closeConnection(connection);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<Book> selectByGenreName(String genreName) {
	    ArrayList<Book> listBook = new ArrayList<>();
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "SELECT book.*, author_name FROM book " +
	        		     "JOIN book_author ON book.isbn = book_author.isbn " +
		                 "JOIN author ON book_author.author_id = author.author_id " +
	                     "JOIN book_genre ON book.isbn = book_genre.isbn " +
	                     "JOIN genre ON book_genre.genre_id = genre.genre_id " +
	                     "WHERE genre.genre_name = N?";

	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, genreName);
	        
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
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

	
	public Book getRandomBook(long userId) {
	    Book book = new Book();

	    try (Connection connection = JDBCUtil.getConnection()) {
	        if (connection != null) {
	        	String sql = "SELECT * FROM book " +
                        "JOIN book_author ON book.isbn = book_author.isbn " +
                        "JOIN author ON book_author.author_id = author.author_id " +
                        "WHERE book.isbn NOT IN (" +
                        "SELECT isbn FROM shelf WHERE user_id = ?" +
                        ") " +
                        "ORDER BY RAND() " +
                        "LIMIT 1";

           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setLong(1, userId);
           ResultSet rs = statement.executeQuery();

	            if (rs.next()) {
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
	        } else {
	            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return book;
	}

	    
public ArrayList<Book> selectByAuthor(String authorName) {
    ArrayList<Book> listBook = new ArrayList<>();
    try {
        Connection connection = JDBCUtil.getConnection();
        String sql = "SELECT book.*, author_name " +
                     "FROM book " +
                     "JOIN book_author ON book.isbn = book_author.isbn " +
                     "JOIN author ON book_author.author_id = author.author_id " +
                     "WHERE author.author_name = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, authorName);
	        
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
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

	public ArrayList<Book> top10Book() {
		ArrayList<Book> listBook = new ArrayList<Book>();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "SELECT b.*, IFNULL(num_reviews, 0) AS num_reviews, author_name " +
		             "FROM book b " +
		             "LEFT JOIN ( " +
		             "    SELECT isbn, COUNT(id) AS num_reviews " +
		             "    FROM review " +
		             "    GROUP BY isbn " +
		             ") r ON b.isbn = r.isbn " +
		             "LEFT JOIN book_author ba ON b.isbn = ba.isbn " +
		             "LEFT JOIN author ON ba.author_id = author.author_id " +
		             "ORDER BY num_reviews DESC " +
		             "LIMIT 10;";

			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
			    Book book = new Book();
			    book.setName(rs.getString("book_title"));
			    book.setBookID(rs.getString("isbn"));
			    book.setAverageRating(rs.getFloat("average_rating"));
			    book.setAuthor(rs.getString("author_name"));
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

	public ArrayList<Book> BooksAddedRecently(Long user_id) {
		ArrayList<Book> listBook = new ArrayList<Book>();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "SELECT DISTINCT b.*, author_name " +
                    "FROM book b " +
                    "LEFT JOIN book_author ba ON b.isbn = ba.isbn " +
                    "LEFT JOIN author ON ba.author_id = author.author_id " +
                    "WHERE EXISTS ( " +
                    "    SELECT 1 " +
                    "    FROM shelf t " +
                    "    WHERE t.user_id = ? " +
                    "    AND t.isbn = b.isbn " +
                    "    ORDER BY t.added_date DESC " +
                    "    LIMIT 20 " +
                    ");";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, user_id);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
			    Book book = new Book();
			    book.setName(rs.getString("book_title"));
			    book.setBookID(rs.getString("isbn"));
			    book.setAverageRating(rs.getFloat("average_rating"));
			    book.setAuthor(rs.getString("author_name"));
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
		
		reverseArrayList(listBook);
		return listBook;
		}
	
	public static void reverseArrayList(ArrayList<Book> list) {
        Collections.reverse(list);
    }
}
