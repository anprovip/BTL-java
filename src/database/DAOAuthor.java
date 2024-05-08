package database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import model.Author;


public class DAOAuthor implements DAOInterface<Author> {
	
	public static DAOAuthor getInstance() {
		return new DAOAuthor();
	}
	@Override
	public void insert(Author t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Author t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Author t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Author> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author selectByID(Author t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Author> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	public Author selectByName(String condition) {
		Author author = new Author();
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql = "SELECT * FROM author WHERE author_name = ?";
			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, condition);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
                author.setAuthorID(rs.getInt("author_id"));
                author.setAuthorName(rs.getString("author_name"));
                author.setAuthorInfo(rs.getString("author_info"));
			    
			    Blob imageBlob = rs.getBlob("author_image");
			    
			    if (imageBlob != null) {
			        // Chuyển đổi Blob thành mảng byte
			        byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
			        
			        // Lưu dữ liệu ảnh vào thuộc tính imageBook của đối tượng Book
			        author.setImageAuthor(new SerialBlob(imageData));
			    }
                System.out.println(author.getAuthorInfo());
			    
			}
			JDBCUtil.closeConnection(connection);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return author;
	}
}
