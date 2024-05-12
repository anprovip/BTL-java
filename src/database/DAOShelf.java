package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.swing.JOptionPane;

import model.Book;
import model.Shelf;
import model.User;

public class DAOShelf implements DAOInterface<Shelf>{
	
	public static DAOShelf getInstance() {
		return new DAOShelf();
	}
	
	@Override
	public void insert(Shelf shelf) {
		try (Connection connection = JDBCUtil.getConnection()) {
			String checkExistBook = "SELECT COUNT(*) AS count FROM shelf WHERE shelf_name = ? AND isbn = ? AND user_id = ?";
	        PreparedStatement checkExistStatement = connection.prepareStatement(checkExistBook);
	        checkExistStatement.setString(1, shelf.getShelfName());
	        checkExistStatement.setString(2, shelf.getBookID());
	        checkExistStatement.setLong(3, User.getInstance().getUserId());
	        ResultSet existResultSet = checkExistStatement.executeQuery();

	        
	        // Nếu đã tồn tại, không thêm mới
	        if (existResultSet.next() && existResultSet.getInt("count") > 0) {
	            System.out.println("You have already added this book to this shelf, you can't add anymore!");
	            JOptionPane.showMessageDialog(null, "You have already added this book to this shelf, you can't add anymore!");
	            return;
	        }
	        
		 	String sql1 = "select user_id from user where username = ?";
		 	
			PreparedStatement st = connection.prepareStatement(sql1);
			st.setString(1, User.getInstance().getUsername());
			ResultSet rs = st.executeQuery();
			int userId = 0;
			if (rs.next()) {
			    userId = rs.getInt("user_id");
			}
	        String sql = "INSERT INTO shelf (shelf_name, user_id, isbn) VALUES (?, ?, ?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, shelf.getShelfName());
	        statement.setInt(2, userId);
	        statement.setString(3, shelf.getBookID());
	        
	        int rowsInserted = statement.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("A new book was inserted in shelf successfully!");
	            JOptionPane.showMessageDialog(null, "Add this book to "+ shelf.getShelfName() +" successfully!");
	        } else {
	            System.out.println("Failed to insert book to the shelf!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	public void insertShelf(Shelf shelf) {
		try (Connection connection = JDBCUtil.getConnection()) {
			
		 	String sql1 = "select user_id from user where username = ?";
		 	
			PreparedStatement st = connection.prepareStatement(sql1);
			st.setString(1, User.getInstance().getUsername());
			ResultSet rs = st.executeQuery();
			int userId = 0;
			if (rs.next()) {
			    userId = rs.getInt("user_id");
			}
	        String sql = "INSERT INTO shelf (shelf_name, user_id) VALUES (?, ?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, shelf.getShelfName());
	        statement.setInt(2, userId);
	        
	        int rowsInserted = statement.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("A new shelf was inserted successfully!");
	            
	        } else {
	            System.out.println("Failed to insert the shelf!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	public void otherUserInsertShelf(Shelf shelf) {
	    try (Connection connection = JDBCUtil.getConnection()) {
	        // Lấy user_id của người dùng đang đăng nhập
	        long loggedInUserId = User.getInstance().getUserId();
	        
	        // Kiểm tra xem shelf_name của shelf được chọn có trùng với shelf_name của user_id được chọn không
	        ArrayList<Shelf> selectedUserShelves = selectByCondition(String.valueOf(loggedInUserId));
	        boolean isSameShelfName = selectedUserShelves.stream().anyMatch(s -> s.getShelfName().equals(shelf.getShelfName()));

	        // Nếu trùng shelf_name, hiển thị thông báo và không thực hiện thêm sách
	        if (isSameShelfName) {
	            System.out.println("This shelf already exists in the user's shelves, cannot add.");
	            JOptionPane.showMessageDialog(null, "You already have this shelf in your shelves, cannot add.");
	            return;
	        }

	        // Tạo một tủ sách mới cho người dùng hiện tại với shelf_name và user_id tương ứng
	        String insertNewShelfSQL = "INSERT INTO shelf (shelf_name, user_id, isbn) VALUES (?, ?, ?)";
	        PreparedStatement insertNewShelfStatement = connection.prepareStatement(insertNewShelfSQL);
	        insertNewShelfStatement.setString(1, shelf.getShelfName());
	        insertNewShelfStatement.setLong(2, loggedInUserId);
	        
	        // Lấy danh sách các cuốn sách từ tủ sách của shelf.getUserId và thêm chúng vào tủ sách mới
	        ArrayList<String> bookIDs = getBooksByShelfNameAndUserID(shelf.getShelfName(), shelf.getUserID());
	        boolean check = true;
	        for (String bookID : bookIDs) {
	            insertNewShelfStatement.setString(3, bookID);
	            int newShelfRowsInserted = insertNewShelfStatement.executeUpdate();
	            if (newShelfRowsInserted > 0) {
	                check = true;
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to insert the new shelf with book!");
	                check = false;
	                return;
	            }
	        }
	        if(check) {
	        	JOptionPane.showMessageDialog(null, "A new shelf with book was inserted successfully!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public ArrayList<String> getBooksByShelfNameAndUserID(String shelfName, long userID) {
	    ArrayList<String> bookIDs = new ArrayList<>();
	    try (Connection connection = JDBCUtil.getConnection()) {
	        String sql = "SELECT isbn FROM shelf WHERE shelf_name = ? AND user_id = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, shelfName);
	        statement.setLong(2, userID);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            bookIDs.add(resultSet.getString("isbn"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookIDs;
	}

	@Override
	public void update(Shelf t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Shelf shelf) {
	    try (Connection connection = JDBCUtil.getConnection()) {
	        String sql = "DELETE FROM shelf WHERE shelf_name = ? AND user_id = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, shelf.getShelfName());
	        statement.setLong(2, User.getInstance().getUserId());

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Deleted shelf(s) successfully!");
	        } else {
	            System.out.println("No shelf was deleted!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public ArrayList<Shelf> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Shelf> selectByCondition(String condition) {
		ArrayList<Shelf> listShelf = new ArrayList<Shelf>();
		
		try {
			Connection connection = JDBCUtil.getConnection();
			 String sql = "SELECT DISTINCT shelf_name, user_id FROM shelf "
	                    + "WHERE user_id = ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, condition);
	            
	            ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Shelf shelf = new Shelf();
			    shelf.setShelfName(rs.getString("shelf_name"));
			    shelf.setUserID(rs.getLong("user_id"));
			    listShelf.add(shelf);
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return listShelf;

	}
	@Override
	public Shelf selectByID(Shelf shelf) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM shelf " +
	                "JOIN book_author ON book_author.isbn = shelf.isbn " +
	                "JOIN book ON book.isbn = shelf.isbn " +
	                "JOIN author ON author.author_id = book_author.author_id " +
	                "WHERE shelf_name = ? AND shelf.user_id = ?";


	        PreparedStatement st = connection.prepareStatement(sql);
	        st.setString(1, shelf.getShelfName());
	        
	        st.setLong(2, shelf.getUserID());
	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {
	        	shelf.setShelfID(rs.getInt("shelf_id"));
	        	shelf.setShelfName(rs.getString("shelf_name"));
	        	shelf.setUserID(rs.getLong("user_id"));
	        	shelf.setBookID(rs.getString("isbn"));
	        	/*
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
	            */
	        }
	        JDBCUtil.closeConnection(connection);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return shelf;
	}
	public boolean deleteBookFromShelf(Book book,String ShelfName ) {
	    try {
	        Connection connection = JDBCUtil.getConnection();
	        String sql = "DELETE FROM shelf WHERE isbn = ? AND user_id = ? AND shelf_name = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, book.getBookID());
	        statement.setLong(2, User.getInstance().getUserId());
	        statement.setString(3,ShelfName );

	        int rowsAffected = statement.executeUpdate();

	        JDBCUtil.closeConnection(connection);

	        // Trả về true nếu có ít nhất một hàng bị ảnh hưởng, tức là sách đã được xóa thành công
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean isBookInShelf(Shelf shelf) {
	    try (Connection connection = JDBCUtil.getConnection()) {
	        String checkExistBook = "SELECT COUNT(*) AS count FROM shelf WHERE shelf_name = ? AND isbn = ? AND user_id = ?";
	        PreparedStatement checkExistStatement = connection.prepareStatement(checkExistBook);
	        checkExistStatement.setString(1, shelf.getShelfName());
	        checkExistStatement.setString(2, shelf.getBookID());
	        checkExistStatement.setLong(3, User.getInstance().getUserId());
	        ResultSet existResultSet = checkExistStatement.executeQuery();

	        // Nếu đã tồn tại, trả về true
	        if (existResultSet.next() && existResultSet.getInt("count") > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public ArrayList<String> getShelfNamesByBookID(String bookID, long userID) {
	    ArrayList<String> shelfNames = new ArrayList<>();
	    try (Connection connection = JDBCUtil.getConnection()) {
	        String sql = "SELECT DISTINCT shelf_name FROM shelf WHERE isbn = ? AND user_id = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, bookID);
	        statement.setLong(2, userID);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            shelfNames.add(resultSet.getString("shelf_name"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return shelfNames;
	}
	// Phương thức để đếm số lượng shelf của một người dùng dựa trên user_id
    public int countShelvesByUserID(long userID) {
        int count = 0;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT COUNT(DISTINCT shelf_name) AS shelfCount FROM shelf WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("shelfCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // Phương thức để lấy tổng số sách trong tất cả các shelf của một người dùng
    public int getTotalBooksByUserID(long userID) {
        int totalCount = 0;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT COUNT(DISTINCT isbn) AS bookCount FROM shelf WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt("bookCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }
}