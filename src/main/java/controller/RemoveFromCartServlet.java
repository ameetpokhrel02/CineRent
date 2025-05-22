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

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[RemoveFromCartServlet] Processing POST request");
        
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
            System.out.println("[RemoveFromCartServlet] Received data: " + data);
            
            // Parse JSON to get movieId
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
            int movieId = jsonObject.get("movieId").getAsInt();
            
            // Remove movie from cart in session
            HttpSession session = request.getSession();
            List<Integer> cartItems = getCartItems(session);
            
            if (cartItems.contains(Integer.valueOf(movieId))) {
                cartItems.remove(Integer.valueOf(movieId));
                session.setAttribute("cart", cartItems);
                System.out.println("[RemoveFromCartServlet] Removed movie ID " + movieId + " from cart");
                out.print("{\"success\":true,\"message\":\"Movie removed from cart\"}");
            } else {
                System.out.println("[RemoveFromCartServlet] Movie ID " + movieId + " not found in cart");
                out.print("{\"success\":false,\"error\":\"Movie not found in cart\"}");
            }
            
        } catch (Exception e) {
            System.err.println("[RemoveFromCartServlet] Error: " + e.getMessage());
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
