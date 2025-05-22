package controller;

import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[UpdateUserServlet] Processing POST request");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            // Read JSON data from request
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            
            String data = buffer.toString();
            System.out.println("Received data: " + data);
            
            // Parse JSON to User object
            Gson gson = new Gson();
            User user = new User();
            
            // Parse the JSON manually to handle field name differences
            com.google.gson.JsonObject jsonObject = gson.fromJson(data, com.google.gson.JsonObject.class);
            
            if (jsonObject.has("id")) {
                user.setId(jsonObject.get("id").getAsInt());
            }
            
            if (jsonObject.has("username")) {
                user.setUsername(jsonObject.get("username").getAsString());
            }
            
            if (jsonObject.has("email")) {
                user.setEmail(jsonObject.get("email").getAsString());
            }
            
            if (jsonObject.has("password")) {
                user.setPassword(jsonObject.get("password").getAsString());
            }
            
            if (jsonObject.has("firstName")) {
                user.setFirst_name(jsonObject.get("firstName").getAsString());
            }
            
            if (jsonObject.has("lastName")) {
                user.setLast_name(jsonObject.get("lastName").getAsString());
            }
            
            if (jsonObject.has("phone")) {
                try {
                    user.setPhone(jsonObject.get("phone").getAsInt());
                } catch (Exception e) {
                    user.setPhone(Integer.parseInt(jsonObject.get("phone").getAsString()));
                }
            }
            
            if (jsonObject.has("role")) {
                user.setRole(jsonObject.get("role").getAsString());
            }
            
            System.out.println("Parsed user: " + user.getUsername());
            
            // Update user in database
            UserDAO dao = new UserDAO();
            boolean success = dao.updateUser(user);
            
            if (success) {
                String jsonResponse = gson.toJson(user);
                System.out.println("Sending response: " + jsonResponse);
                out.print(jsonResponse);
            } else {
                System.out.println("Failed to update user");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"Failed to update user.\"}");
            }
        } catch (Exception e) {
            System.err.println("[UpdateUserServlet] Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
