package model;

import java.sql.Blob;
import java.sql.Date;



public class Review {
	private int reviewId;
	private String ISBN;
	private int userId;
	private String reviewText;
	private int rating;
	private Date reviewDate;
	private Blob userImage ;
	private String username;
	
    public Review() {super();}



	public Review(int reviewId, String iSBN, int userID, String reviewText, int rating, Date reviewDate, Blob userImage, String username) {
		super();
		this.reviewId = reviewId;
		this.ISBN = iSBN;
		this.userId = userID;
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewDate = reviewDate;
		this.userImage = userImage;
		this.username = username;
	}



	public String getISBN() {
		return ISBN;
	}



	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getReviewText() {
		return reviewText;
	}



	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}



	public float getRating() {
		return rating;
	}



	public void setRating(int rating) {
		this.rating = rating;
	}



	public Date getReviewDate() {
		return reviewDate;
	}



	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Blob getUserImage() {
		return userImage;
	}

	public void setUserImage(Blob userImage) {
		this.userImage = userImage;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}

