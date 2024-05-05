package model;

public class Genre {
	private String genreName;
	private String description;
	 
	public Genre(String genreName, String description) {
		super();
		this.genreName = genreName;
		this.description = description;
	}
	public Genre() {super();}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
