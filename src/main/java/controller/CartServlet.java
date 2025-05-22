package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Movie;
import DAO.MovieDAO;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[CartServlet] Processing GET request");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        List<Integer> cartItems = getCartItems(session);
        
        System.out.println("[CartServlet] Cart items: " + cartItems);
        
        if (cartItems.isEmpty()) {
            out.print("[]");
            return;
        }
        
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = new ArrayList<>();
        
        for (Integer movieId : cartItems) {
            Movie movie = movieDAO.getMovieById(movieId);
            if (movie != null) {
                movies.add(movie);
                System.out.println("[CartServlet] Added movie to response: " + movie.getTitle());
            } else {
                System.out.println("[CartServlet] Movie not found for ID: " + movieId);
            }
        }
        
        // Convert movies to JSON
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            sb.append("{");
            sb.append("\"id\":").append(movie.getID()).append(",");
            sb.append("\"title\":\"").append(escapeJson(movie.getTitle())).append("\",");
            sb.append("\"genre\":\"").append(escapeJson(movie.getGenre())).append("\",");
            sb.append("\"releaseYear\":").append(movie.getReleaseYear()).append(",");
            sb.append("\"rating\":").append(movie.getRating()).append(",");
            sb.append("\"price\":").append(parsePrice(movie.getPrice())).append(",");
            sb.append("\"runtime\":\"").append(escapeJson(movie.getRuntime())).append("\",");
            
            // Add poster path if available
            String posterPath = movie.getPosterPath();
            if (posterPath != null && !posterPath.isEmpty()) {
                sb.append("\"posterPath\":\"").append(escapeJson(posterPath)).append("\"");
            } else {
                sb.append("\"posterPath\":\"\"");
            }
            
            sb.append("}");
            
            if (i < movies.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        String jsonOutput = sb.toString();
        System.out.println("[CartServlet] JSON response: " + jsonOutput);
        out.print(jsonOutput);
    }
    
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) {
            return 0.0;
        }
        
        try {
            // Remove $ sign, commas, and trim whitespace
            String cleanPrice = priceStr.replace("$", "").replace(",", "").trim();
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            System.err.println("[CartServlet] Could not parse price: " + priceStr);
            return 0.0;
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Integer> getCartItems(HttpSession session) {
        List<Integer> cartItems = (List<Integer>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute("cart", cartItems);
        }
        return cartItems;
    }
}







