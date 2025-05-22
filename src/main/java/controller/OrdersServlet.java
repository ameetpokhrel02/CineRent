package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Movie;
import model.Sale;
import model.User;
import DAO.MovieDAO;
import DAO.SaleDAO;

@WebServlet("/OrdersServlet")
public class OrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[OrdersServlet] Processing GET request");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Check if user is logged in
        if (user == null) {
            out.print("{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }
        
        // Get user ID
        int userId = user.getId();
        
        // Get user's orders
        SaleDAO saleDAO = new SaleDAO();
        List<Sale> sales = saleDAO.getSalesByUserId(userId);
        
        // Group sales by purchase date to form orders
        Map<String, List<Map<String, Object>>> orders = new HashMap<>();
        MovieDAO movieDAO = new MovieDAO();
        
        for (Sale sale : sales) {
            String purchaseDate = sale.getPurchaseDate().toString();
            
            // Create order if it doesn't exist
            if (!orders.containsKey(purchaseDate)) {
                orders.put(purchaseDate, new ArrayList<>());
            }
            
            // Get movie details
            Movie movie = movieDAO.getMovieById(sale.getMovieId());
            
            // Add sale to order
            Map<String, Object> saleDetails = new HashMap<>();
            saleDetails.put("id", sale.getId());
            saleDetails.put("movieId", sale.getMovieId());
            saleDetails.put("movieTitle", movie != null ? movie.getTitle() : "Unknown Movie");
            saleDetails.put("price", sale.getPrice());
            
            orders.get(purchaseDate).add(saleDetails);
        }
        
        // Convert to JSON-friendly format
        List<Map<String, Object>> ordersList = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : orders.entrySet()) {
            Map<String, Object> order = new HashMap<>();
            order.put("date", entry.getKey());
            order.put("items", entry.getValue());
            
            // Calculate total
            double total = 0;
            for (Map<String, Object> item : entry.getValue()) {
                total += (double) item.get("price");
            }
            order.put("total", total);
            
            ordersList.add(order);
        }
        
        // Return orders as JSON
        out.print(new com.google.gson.Gson().toJson(ordersList));
    }
}