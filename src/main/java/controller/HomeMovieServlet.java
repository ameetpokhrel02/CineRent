package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAO.MovieDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

@WebServlet("/HomeMovieServlet")
public class HomeMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[HomeMovieServlet] Received GET request for home page movies.");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            MovieDAO dao = new MovieDAO();
            List<Movie> movies = dao.getallMovies();

            System.out.println("[HomeMovieServlet] Retrieved " + movies.size() + " movies from DAO.");

            if (movies.isEmpty()) {
                System.out.println("[HomeMovieServlet] No movies found in database.");
                response.setStatus(404); // Not Found
                out.print("{\"error\": \"No movies found in database. Please add some movies first.\", \"code\": 404}");
                out.flush();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);

                // Convert genre string to array (assuming genres are comma-separated)
                String[] genresArray = movie.getGenre() != null ?
                    movie.getGenre().split(",\\s*") :
                    new String[]{"Drama"};

                // Convert release year to date format (YYYY-MM-DD)
                String releaseDate = movie.getReleaseYear() + "-01-01";

                // Get runtime as integer (assuming runtime is stored as "X min" or just "X")
                String runtimeStr = movie.getRuntime() != null ?
                    movie.getRuntime().replaceAll("[^0-9]", "") :
                    "120";
                int runtime = 120; // Default runtime if parsing fails
                try {
                    runtime = Integer.parseInt(runtimeStr);
                } catch (NumberFormatException e) {
                    System.out.println("[HomeMovieServlet] Could not parse runtime: " + movie.getRuntime());
                }

            // Get poster path from database or use default
            String posterPath = movie.getPosterPath();
            if (posterPath == null || posterPath.isEmpty()) {
                posterPath = "https://image.tmdb.org/t/p/w500/placeholder_poster.jpg";
            }

            // For backdrop, we can use the same poster path or a default
            String backdropPath = posterPath;
            if (backdropPath == null || backdropPath.isEmpty()) {
                backdropPath = "https://image.tmdb.org/t/p/original/placeholder_backdrop.jpg";
            }

            // Process YouTube URL to ensure it's in embed format
            String videoUrl = movie.getYoutubeLink();
            if (videoUrl != null && !videoUrl.isEmpty()) {
                // Check if it's already an embed URL
                if (!videoUrl.contains("embed")) {
                    // Convert regular YouTube URL to embed format
                    if (videoUrl.contains("watch?v=")) {
                        // Format: https://www.youtube.com/watch?v=VIDEO_ID
                        String videoId = videoUrl.split("watch\\?v=")[1];
                        // Remove any additional parameters
                        if (videoId.contains("&")) {
                            videoId = videoId.split("&")[0];
                        }
                        videoUrl = "https://www.youtube.com/embed/" + videoId;
                    } else if (videoUrl.contains("youtu.be/")) {
                        // Format: https://youtu.be/VIDEO_ID
                        String videoId = videoUrl.split("youtu\\.be/")[1];
                        // Remove any additional parameters
                        if (videoId.contains("?")) {
                            videoId = videoId.split("\\?")[0];
                        }
                        videoUrl = "https://www.youtube.com/embed/" + videoId;
                    }
                }
            } else {
                // Default video if none provided
                videoUrl = "https://www.youtube.com/embed/dQw4w9WgXcQ"; // Sample video
            }

            // Get overview from database or use default
            String overview = movie.getOverview();
            if (overview == null || overview.isEmpty()) {
                overview = "Watch this amazing movie now!";
            }

            sb.append("{")
              .append("\"id\": ").append(movie.getID()).append(", ")
              .append("\"title\": \"").append(escapeJson(movie.getTitle())).append("\", ")
              .append("\"overview\": \"").append(escapeJson(overview)).append("\", ")
              .append("\"posterPath\": \"").append(escapeJson(posterPath)).append("\", ")
              .append("\"backdropPath\": \"").append(escapeJson(backdropPath)).append("\", ")
              .append("\"releaseDate\": \"").append(releaseDate).append("\", ")
              .append("\"voteAverage\": ").append(movie.getRating()).append(", ")
              .append("\"genres\": ").append(convertToJsonArray(genresArray)).append(", ")
              .append("\"runtime\": ").append(runtime).append(", ")
              .append("\"videoUrl\": \"").append(escapeJson(videoUrl)).append("\"")
              .append("}");

            if (i < movies.size() - 1) {
                sb.append(", ");
            }
        }

            sb.append("]");
            String jsonOutput = sb.toString();

            System.out.println("[HomeMovieServlet] Final JSON output (truncated):");
            if (jsonOutput.length() > 200) {
                System.out.println(jsonOutput.substring(0, 200) + "...");
            } else {
                System.out.println(jsonOutput);
            }

            out.print(jsonOutput);
            out.flush();
        } catch (Exception e) {
            System.err.println("[HomeMovieServlet] Error: " + e.getMessage());
            e.printStackTrace();

            response.setStatus(500); // Internal Server Error
            out.print("{\"error\": \"" + escapeJson(e.getMessage()) + "\", \"code\": 500}");
            out.flush();
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    private String convertToJsonArray(String[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < values.length; i++) {
            sb.append("\"").append(escapeJson(values[i].trim())).append("\"");
            if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
