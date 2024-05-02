package model;

import java.sql.Blob;

public class Book {
	private String bookID;
	private String name;
	private String ImageSrc;
	private String author;
	private Blob imageBook;
	private int publishDate;
	private float averageRating;
	private String shelfName;
	
	

	public Book(String bookID, String name, String imageSrc, String author, Blob imageBook, int publishDate, float averageRating, String shelfName) {
		super();
		this.bookID = bookID;
		this.name = name;
		ImageSrc = imageSrc;
		this.author = author;
		this.imageBook = imageBook;
		this.publishDate = publishDate;
		this.averageRating = averageRating;
		this.shelfName = shelfName;
	}
	
	public Book() {super();}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageSrc() {
		return ImageSrc;
	}

	public void setImageSrc(String imageSrc) {
		ImageSrc = imageSrc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Blob getImageBook() {
		return imageBook;
	}

	public void setImageBook(Blob imageBook) {
		this.imageBook = imageBook;
	}

	public int getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(int publishDate) {
		this.publishDate = publishDate;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public String getShelfName() {
		return shelfName;
	}

	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}

	
	
}
