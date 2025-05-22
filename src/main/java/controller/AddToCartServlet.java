package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[AddToCartServlet] Processing POST request");
        
        response.setContentType("application/json");
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
            System.out.println("[AddToCartServlet] Received data: " + data);
            
            // Parse JSON to get movieId
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
            int movieId = jsonObject.get("movieId").getAsInt();
            
            System.out.println("[AddToCartServlet] Adding movie ID: " + movieId);
            
            // Add movie to cart in session
            HttpSession session = request.getSession();
            List<Integer> cartItems = getCartItems(session);
            
            // Check if movie is already in cart
            if (!cartItems.contains(movieId)) {
                cartItems.add(movieId);
                session.setAttribute("cart", cartItems);
                System.out.println("[AddToCartServlet] Added movie ID " + movieId + " to cart. Cart now contains: " + cartItems);
                out.print("{\"success\":true,\"message\":\"Movie added to cart\"}");
            } else {
                System.out.println("[AddToCartServlet] Movie ID " + movieId + " already in cart");
                out.print("{\"success\":true,\"message\":\"Movie already in cart\"}");
            }
            
        } catch (Exception e) {
            System.err.println("[AddToCartServlet] Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
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


