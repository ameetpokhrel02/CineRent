package controller;

import DAO.MovieDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteMovieServlet")
public class DeleteMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            MovieDAO dao = new MovieDAO();
            boolean success = dao.deleteMovie(id);
            response.getWriter().println(success ? "Movie deleted successfully." : "Failed to delete movie.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
