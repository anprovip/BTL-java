package model;

import java.sql.Date;

public class Review {
	private String ISBN;
	private long userId;
	private String reviewText;
	private int rating;
	private Date reviewDate;
	

	
    public Review() {super();}



	public Review(String iSBN, int userID, String reviewText, int rating, Date reviewDate) {
		super();
		ISBN = iSBN;
		this.userId = userID;
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}



	public String getISBN() {
		return ISBN;
	}



	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String getReviewText() {
		return reviewText;
	}



	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}



	public int getRating() {
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

	 
}

