package database;

import java.util.ArrayList;

import model.Follow;

public class DAOFollow implements DAOInterface<Follow>{

	public static DAOFollow getInstance() {
		return new DAOFollow();
	}
	
	@Override
	public void insert(Follow t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Follow t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Follow t) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
