package model;

public class Shelf {
	private int shelfID;
	private String shelfName;
	private long userID;
	private String bookID;
	public Shelf() {super();}
	public Shelf(int shelfID, String shelfName, long userID, String bookID) {
		super();
		this.shelfID = shelfID;
		this.shelfName = shelfName;
		this.userID = userID;
		this.bookID = bookID;
	}
	public int getShelfID() {
		return shelfID;
	}
	public void setShelfID(int shelfID) {
		this.shelfID = shelfID;
	}
	public String getShelfName() {
		return shelfName;
	}
	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	
}
