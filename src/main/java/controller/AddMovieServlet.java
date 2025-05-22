package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import DAO.MovieDAO;

@WebServlet("/AddMovieServlet")
public class AddMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("AddMovieServlet: doPost method called");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Read the request body
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            System.out.println("Received data: " + data);
            
            // Parse JSON to Movie object
            Gson gson = new Gson();
            Movie movie = gson.fromJson(data, Movie.class);
            System.out.println("Parsed movie: " + movie.getTitle());

            // Add movie to database
            MovieDAO dao = new MovieDAO();
            int generatedId = dao.addMovie(movie);
            System.out.println("Generated ID: " + generatedId);
            
            if (generatedId > 0) {
                movie.setID(generatedId);
                String jsonResponse = gson.toJson(movie);
                System.out.println("Sending response: " + jsonResponse);
                out.print(jsonResponse);
            } else {
                System.out.println("Failed to add movie");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"Failed to add movie.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        } finally {
            out.flush();
        }
    }
}
