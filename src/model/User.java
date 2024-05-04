package model;

import java.sql.Blob;

public class User {
	private long userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Blob imageUser;
    private String imageSrc;
    private static final User instance = new User();
    public static User getInstance() {
        return instance;
    }
    public User() {super();};    
	public User(String username, String password, String email, String phoneNumber, Blob imageUser, String imageSrc) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.imageSrc = imageSrc;
		this.imageUser = imageUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Blob getImageUser() {
		return imageUser;
	}
	public void setImageUser(Blob imageUser) {
		this.imageUser = imageUser;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", imageUser=" + imageUser + ", imageSrc=" + imageSrc + "]";
	}
	public void clearUserData() {
        setUsername(null);
        setPassword(null);
        setEmail(null);
        setPhoneNumber(null);
        setImageUser(null);
        setUserId(-1);
        // Xóa dữ liệu khác nếu cần
    }
	
    
}