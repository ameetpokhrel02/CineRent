package controller;

import model.Movie;
import DAO.MovieDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdateMovieServlet")
public class UpdateMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
            float rating = Float.parseFloat(request.getParameter("rating"));
            String price = request.getParameter("price");
            String runtime = request.getParameter("runtime");
            String youtubeLink = request.getParameter("youtubeLink");

            Movie movie = new Movie(title, genre, releaseYear, rating, price, runtime, youtubeLink);
            movie.setID(id);

            MovieDAO dao = new MovieDAO();
            boolean success = dao.updateMovie(id,movie);

            response.getWriter().println(success ? "Movie updated successfully." : "Failed to update movie.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
