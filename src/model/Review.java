package model;

import java.sql.Date;

public class Review {
    private int reviewId;
    private String reviewContent;
    private int reviewRating;
    private Date dateSubmitted;
	
  

	public Review(int reviewId, String reviewContent, int reviewRating, Date dateSubmitted) {
		super();
		this.reviewId = reviewId;
		this.reviewContent = reviewContent;
		this.reviewRating = reviewRating;
		this.dateSubmitted = dateSubmitted;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public int getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}

    

}

