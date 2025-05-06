package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import DAO.MovieDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

@WebServlet("/ListMovieServlet")
public class ListMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[Servlet] Received GET request for movies.");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getallMovies();

        System.out.println("[Servlet] Retrieved " + movies.size() + " movies from DAO.");

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println("[Servlet] Processing movie: " + movie.getTitle());

            sb.append("{")
              .append("\"id\": ").append(movie.getID()).append(", ")
              .append("\"title\": \"").append(escapeJson(movie.getTitle())).append("\", ")
              .append("\"genre\": \"").append(escapeJson(movie.getGenre())).append("\", ")
              .append("\"releaseYear\": ").append(movie.getReleaseYear()).append(", ")
              .append("\"rating\": ").append(movie.getRating()).append(", ")
              .append("\"price\": \"").append(escapeJson(movie.getPrice())).append("\", ")
              .append("\"runtime\": \"").append(escapeJson(movie.getRuntime())).append("\", ")
              .append("\"youtubeLink\": \"").append(escapeJson(movie.getYoutubeLink())).append("\", ")
              .append("\"overview\": \"").append(escapeJson(movie.getOverview())).append("\", ")
              .append("\"posterPath\": \"").append(escapeJson(movie.getPosterPath())).append("\"")
              .append("}");

            if (i < movies.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        String jsonOutput = sb.toString();

        System.out.println("[Servlet] Final JSON output:");
        System.out.println(jsonOutput);

        PrintWriter out = response.getWriter();
        out.print(jsonOutput);
        out.flush();
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }
}
