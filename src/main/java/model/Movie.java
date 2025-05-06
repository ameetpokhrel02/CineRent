package model;


public class Movie {

	private int ID;
	private String Title;
	private String Genre;
	private int ReleaseYear;
	private float Rating;
	private String Price;
	private String Runtime;
	private String youtubeLink;
	private String overview;
	private String posterPath;

	public Movie(String title, String genre, int releaseYear, float rating, String price, String runtime, String youtubeLink) {
		super();
		this.Title = title;
		this.Genre = genre;
		this.ReleaseYear = releaseYear;
		this.Rating = rating;
		this.Price = price;
		this.Runtime = runtime;
		this.youtubeLink = youtubeLink;
		this.overview = "";
		this.posterPath = "";
	}

	public Movie(String title, String genre, int releaseYear, float rating, String price, String runtime, String youtubeLink, String overview, String posterPath) {
		super();
		this.Title = title;
		this.Genre = genre;
		this.ReleaseYear = releaseYear;
		this.Rating = rating;
		this.Price = price;
		this.Runtime = runtime;
		this.youtubeLink = youtubeLink;
		this.overview = overview;
		this.posterPath = posterPath;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		Genre = genre;
	}

	public int getReleaseYear() {
		return ReleaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		ReleaseYear = releaseYear;
	}

	public float getRating() {
		return Rating;
	}

	public void setRating(float rating) {
		Rating = rating;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getRuntime() {
		return Runtime;
	}

	public void setRuntime(String runtime) {
		Runtime = runtime;
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
