package model;

public class Follow {
	private int followId;
    private int followerId;
    private int followingId;
    private int userId;
    private static final Follow instance = new Follow();
    public static Follow getInstance() {
        return instance;
    }
    public Follow() {super();}
	public Follow(int followId, int followerId, int followingId, int userId) {
		super();
		this.followId = followId;
		this.followerId = followerId;
		this.followingId = followingId;
		this.userId = userId;
	}
	public int getFollowId() {
		return followId;
	}
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	public int getFollowerId() {
		return followerId;
	}
	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}
	public int getFollowingId() {
		return followingId;
	}
	public void setFollowingId(int followingId) {
		this.followingId = followingId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
    
}
