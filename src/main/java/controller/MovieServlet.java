package controller;
import model.Movie;
import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;


@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

		try {
			System.out.print("Servlet call vairaxa");
			String title=request.getParameter("title");
			String genre = request.getParameter("genre");
	        int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
	        float rating = Float.parseFloat(request.getParameter("rating"));
	        String price = request.getParameter("price");
	        String runtime = request.getParameter("runtime");
	        String youtubeLink = request.getParameter("youtubeLink");

	        // Get the new fields
	        String overview = request.getParameter("overview");
	        String posterPath = request.getParameter("posterPath");

            String jsResponse = "<script type=\"text/javascript\">\n" +
                    "console.log('Received movie details:');\n" +
                    "console.log('Title: " + title + "');\n" +
                    "console.log('Genre: " + genre + "');\n" +
                    "console.log('Release Year: " + releaseYear + "');\n" +
                    "console.log('Rating: " + rating + "');\n" +
                    "console.log('Price: " + price + "');\n" +
                    "console.log('Runtime: " + runtime + "');\n" +
                    "console.log('YouTube Link: " + youtubeLink + "');\n" +
                    "console.log('Overview: " + overview + "');\n" +
                    "console.log('Poster Path: " + posterPath + "');\n" +
                    "</script>";

	        // Create movie with the new constructor
	        Movie movie = new Movie(title, genre, releaseYear, rating, price, runtime, youtubeLink);

	        // Set the new fields
	        if (overview != null && !overview.isEmpty()) {
	            movie.setOverview(overview);
	        }

	        if (posterPath != null && !posterPath.isEmpty()) {
	            movie.setPosterPath(posterPath);
	        }

	        DAO.MovieDAO dao = new DAO.MovieDAO();

	        boolean success = dao.addMovie(movie);
	        if (success) {
                jsResponse += "<script type=\"text/javascript\">\n" +
                    "console.log('Movie added successfully');\n" +
                    "</script>";
                response.getWriter().println("Movie added");
            } else {
                jsResponse += "<script type=\"text/javascript\">\n" +
                    "console.log('Failed to add movie');\n" +
                    "</script>";
                response.getWriter().println("Movie failed.");
            }

		} catch (Exception E){
			E.printStackTrace();
			response.getWriter().println("Error during registration:" + E.getMessage());
	}
}




	}