package model;

public class Movie {
	private int ID;
	private String title;
	private String genre;
	private int releaseYear;
	private float rating;
	private String price;
	private String runtime;
	private String youtubeLink;
	private String overview;
	private String posterPath;
	
	// Default constructor for Gson deserialization
	public Movie() {
	}
	
	public Movie(String title, String genre, int releaseYear, float rating, String price, String runtime, String youtubeLink) {
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.rating = rating;
		this.price = price;
		this.runtime = runtime;
		this.youtubeLink = youtubeLink;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
}
