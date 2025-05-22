package controller;

import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ListUserServlet")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[ListUserServlet] Received GET request for users.");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            UserDAO dao = new UserDAO();
            List<User> users = dao.getAllUsers();

            System.out.println("[ListUserServlet] Retrieved " + users.size() + " users from DAO.");
            
            // Use Gson to convert the list to JSON
            Gson gson = new GsonBuilder().serializeNulls().create();
            String jsonResponse = gson.toJson(users);
            
            System.out.println("[ListUserServlet] JSON response: " + jsonResponse);
            out.write(jsonResponse);
        } catch (Exception e) {
            System.err.println("[ListUserServlet] Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}





