package model;

import java.sql.Blob;

public class Author {
	private int authorID;
	private String authorName;
	private String authorInfo;
	private Blob imageAuthor;
	public Author(int authorID, String authorName, String authorInfo, Blob imageAuthor) {
		super();
		this.authorID = authorID;
		this.authorName = authorName;
		this.authorInfo = authorInfo;
		this.imageAuthor = imageAuthor;
	}
	
	public Author() {super();}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorInfo() {
		return authorInfo;
	}

	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}

	public Blob getImageAuthor() {
		return imageAuthor;
	}

	public void setImageAuthor(Blob imageAuthor) {
		this.imageAuthor = imageAuthor;
	}
	
	
}
