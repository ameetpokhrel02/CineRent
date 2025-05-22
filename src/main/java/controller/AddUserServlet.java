package controller;

import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.HashUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[AddUserServlet] Processing POST request");
        
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
            System.out.println("[AddUserServlet] Received data: " + data);
            
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
                user.setPassword(HashUtil.hash(jsonObject.get("password").getAsString()));
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
            
            System.out.println("[AddUserServlet] Parsed user: " + user.getUsername());
            
            // Add user to database
            UserDAO dao = new UserDAO();
            boolean success = dao.insertUser(user);
            
            if (success) {
                String jsonResponse = gson.toJson(user);
                System.out.println("[AddUserServlet] Sending response: " + jsonResponse);
                out.print(jsonResponse);
            } else {
                System.out.println("[AddUserServlet] Failed to add user");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"Failed to add user.\"}");
            }
        } catch (Exception e) {
            System.err.println("[AddUserServlet] Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}





