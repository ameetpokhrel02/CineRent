package controller;

import model.Movie;
import DAO.MovieDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/GetMovieServlet")
public class GetMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            MovieDAO dao = new MovieDAO();
            Movie movie = dao.getMovieById(id);

            if (movie != null) {
                response.getWriter().println("Movie: " + movie.getTitle() + ", Genre: " + movie.getGenre());
            } else {
                response.getWriter().println("Movie not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
