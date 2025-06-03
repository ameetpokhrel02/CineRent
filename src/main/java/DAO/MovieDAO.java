package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class MovieDAO {
	private Connection connection;

	public MovieDAO() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// make correction in db name here
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cinerent?useSSL=false", "root", "");


	}catch(Exception e) {
		e.printStackTrace();

		throw new RuntimeException("Failed to connect to the database",e);
	}
}


public int addMovie(Movie movie) throws SQLException {
    String sql = "INSERT INTO movie (Title, Genre, ReleaseYear, Rating, Price, Runtime, youtubeLink, overview, posterPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getReleaseYear());
        stmt.setFloat(4, movie.getRating());
        stmt.setString(5, movie.getPrice());
        stmt.setString(6, movie.getRuntime());
        stmt.setString(7, movie.getYoutubeLink());
        stmt.setString(8, movie.getOverview());
        stmt.setString(9, movie.getPosterPath());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
}



		public List<Movie> getallMovies(){

			List<Movie> movies = new ArrayList<>();

			String sql = "SELECT * FROM movie";
			System.out.println("render happening?");
			try(PreparedStatement stmt =connection.prepareStatement(sql)){
				ResultSet rs = stmt.executeQuery();
				 while (rs.next()) {
					 System.out.println("Fetched row: " + rs.getString("Title"));

					 // Try to get overview and posterPath, but handle case where columns might not exist yet
					 String overview = "";
					 String posterPath = "";
					 try {
						 overview = rs.getString("overview");
						 posterPath = rs.getString("posterPath");
					 } catch (SQLException ex) {
						 System.out.println("Note: overview or posterPath columns might not exist yet: " + ex.getMessage());
					 }

		                Movie movie = new Movie(
		                        rs.getString("Title"),
		                        rs.getString("Genre"),
		                        rs.getInt("ReleaseYear"),
		                        rs.getFloat("Rating"),
		                        rs.getString("Price"),
		                        rs.getString("Runtime"),
		                        rs.getString("youtubeLink")
		                );
		                movie.setID(rs.getInt("ID"));

		                // Set the new fields
		                if (overview != null) movie.setOverview(overview);
		                if (posterPath != null) movie.setPosterPath(posterPath);

		                movies.add(movie);

			}}catch(Exception e) {
				e.printStackTrace();
			}
				return movies;
			}






		 // Read a movie by ID
	    public Movie getMovieById(int id) {
	        String sql = "SELECT * FROM movie WHERE ID = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    // Try to get overview and posterPath, but handle case where columns might not exist yet
	                    String overview = "";
	                    String posterPath = "";
	                    try {
	                        overview = rs.getString("overview");
	                        posterPath = rs.getString("posterPath");
	                    } catch (SQLException ex) {
	                        System.out.println("Note: overview or posterPath columns might not exist yet: " + ex.getMessage());
	                    }

	                    Movie movie = new Movie(
	                            rs.getString("Title"),
	                            rs.getString("Genre"),
	                            rs.getInt("ReleaseYear"),
	                            rs.getFloat("Rating"),
	                            rs.getString("Price"),
	                            rs.getString("Runtime"),
	                            rs.getString("youtubeLink")
	                    );

	                    movie.setID(rs.getInt("ID"));

	                    // Set the new fields
	                    if (overview != null) movie.setOverview(overview);
	                    if (posterPath != null) movie.setPosterPath(posterPath);

	                    return movie;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }





	    // Update Movie
	    public boolean updateMovie(int id, Movie movie) {
	        String sql = "UPDATE movie SET Title = ?, Genre = ?, ReleaseYear = ?, Rating = ?, Price = ?, Runtime = ?, youtubeLink = ?, overview = ?, posterPath = ? WHERE ID = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, movie.getTitle());
	            stmt.setString(2, movie.getGenre());
	            stmt.setInt(3, movie.getReleaseYear());
	            stmt.setFloat(4, movie.getRating());
	            stmt.setString(5, movie.getPrice());
	            stmt.setString(6, movie.getRuntime());
	            stmt.setString(7, movie.getYoutubeLink());
	            stmt.setString(8, movie.getOverview());
	            stmt.setString(9, movie.getPosterPath());
	            stmt.setInt(10, id);
	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    // Delete Movie
	    public boolean deleteMovie(int id) {
	        String sql = "DELETE FROM movie WHERE ID = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }



	}










