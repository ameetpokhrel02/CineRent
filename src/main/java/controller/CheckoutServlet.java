package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Movie;
import model.User;
import DAO.MovieDAO;
import DAO.SaleDAO;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[CheckoutServlet] Processing POST request");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        System.out.println("[CheckoutServlet] Session ID: " + session.getId());
        
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("[CheckoutServlet] Authentication required - no user logged in");
            out.print("{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }
        
        System.out.println("[CheckoutServlet] User found in session: " + user.getUsername() + ", Role: " + user.getRole());
        
        // Check if user has the correct role
        String role = user.getRole();
        if (!"user".equalsIgnoreCase(role)) {
            System.out.println("[CheckoutServlet] Unauthorized role: " + role);
            out.print("{\"success\":false,\"error\":\"Unauthorized role\"}");
            return;
        }
        
        // Get user ID
        Integer userId = user.getId();
        System.out.println("[CheckoutServlet] User ID: " + userId);
        
        List<Integer> cartItems = getCartItems(session);
        System.out.println("[CheckoutServlet] Cart items: " + cartItems);
        
        if (cartItems.isEmpty()) {
            out.print("{\"success\":false,\"error\":\"Cart is empty\"}");
            return;
        }
        
        // Get current date
        Date purchaseDate = Date.valueOf(LocalDate.now());
        
        // Process each movie in cart
        MovieDAO movieDAO = new MovieDAO();
        SaleDAO saleDAO = new SaleDAO();
        boolean allSuccess = true;
        StringBuilder errorMessages = new StringBuilder();
        
        for (Integer movieId : cartItems) {
            Movie movie = movieDAO.getMovieById(movieId);
            if (movie != null) {
                try {
                    System.out.println("[CheckoutServlet] Processing movie: " + movie.getTitle() + ", Price: " + movie.getPrice());
                    
                    // Improved price parsing
                    String priceStr = movie.getPrice();
                    double price = 0.0;
                    
                    // Handle different price formats
                    if (priceStr != null && !priceStr.isEmpty()) {
                        // Remove $ sign, commas, and trim whitespace
                        priceStr = priceStr.replace("$", "").replace(",", "").trim();
                        
                        try {
                            price = Double.parseDouble(priceStr);
                        } catch (NumberFormatException e) {
                            System.err.println("[CheckoutServlet] Could not parse price: " + priceStr);
                            // Default to 0 or some other value if parsing fails
                            price = 0.0;
                        }
                    }
                    
                    System.out.println("[CheckoutServlet] Parsed price: " + price);
                    
                    // Record the sale
                    boolean success = saleDAO.addSale(movieId, userId, purchaseDate, price);
                    if (!success) {
                        allSuccess = false;
                        String error = "Failed to record sale for movie ID: " + movieId;
                        System.err.println("[CheckoutServlet] " + error);
                        errorMessages.append(error).append("; ");
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    String error = "Error processing movie ID: " + movieId + " - " + e.getMessage();
                    System.err.println("[CheckoutServlet] " + error);
                    errorMessages.append(error).append("; ");
                }
            } else {
                allSuccess = false;
                String error = "Movie not found for ID: " + movieId;
                System.err.println("[CheckoutServlet] " + error);
                errorMessages.append(error).append("; ");
            }
        }
        
        if (allSuccess) {
            // Clear the cart
            session.removeAttribute("cart");
            System.out.println("[CheckoutServlet] Purchase completed successfully, cart cleared");
            out.print("{\"success\":true,\"message\":\"Purchase completed successfully\"}");
        } else {
            String errorMsg = errorMessages.length() > 0 ? errorMessages.toString() : "Failed to process some items";
            System.out.println("[CheckoutServlet] Checkout failed: " + errorMsg);
            out.print("{\"success\":false,\"error\":\"" + errorMsg + "\"}");
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







