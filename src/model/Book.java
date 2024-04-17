package model;

import java.sql.Blob;

public class Book {
	private int bookID;
	private String name;
	private String ImageSrc;
	private String author;
	private Blob imageBook;
	
	public Book(int bookID, String name, String imageSrc, String author) {
		super();
		this.bookID = bookID;
		this.name = name;
		ImageSrc = imageSrc;
		this.author = author;
	}
	public Book() {};
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
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
	
	
	
}
