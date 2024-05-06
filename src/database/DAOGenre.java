package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Genre;

public class DAOGenre implements DAOInterface<Genre> {

	public static DAOGenre getInstance() {
		return new DAOGenre();
	}
	
	@Override
	public void insert(Genre t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Genre t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Genre t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Genre selectByID(Genre t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Genre> selectByCondition(String condition) {
		// Hàm lấy thể loại theo mã sách
		ArrayList<Genre> genresOfBook = new ArrayList<>();
		
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql ="SELECT * FROM genre join book_genre on genre.genre_id=book_genre.genre_id where isbn = ?;";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, condition);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Genre genre = new Genre();
				genre.setGenreID(rs.getInt("genre_id"));
				genre.setGenreName(rs.getString("genre_name"));
				genre.setDescription(rs.getString("description"));
				genresOfBook.add(genre);
			}
			
			JDBCUtil.closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genresOfBook;
	}

	
	public Genre selectByName(String genreName) {
		Genre genre = new Genre();
		
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql ="SELECT * FROM Genre where genre_name = ?;";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, genreName);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				genre.setGenreID(rs.getInt("genre_id"));
				genre.setGenreName(rs.getString("genre_name"));
				genre.setDescription(rs.getString("description"));
			}
			
			JDBCUtil.closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return genre;
	}

	@Override
	public ArrayList<Genre> selectAll() {
		ArrayList<Genre> allGenres = new ArrayList<>();
		
		try {
			Connection connection = JDBCUtil.getConnection();
			String sql ="SELECT * FROM Genre;";
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Genre genre = new Genre();
				genre.setGenreID(rs.getInt("genre_id"));
				genre.setGenreName(rs.getString("genre_name"));
				genre.setDescription(rs.getString("description"));
				allGenres.add(genre);
			}
			
			JDBCUtil.closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allGenres;
	}

}
