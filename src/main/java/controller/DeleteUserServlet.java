package controller;

import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[DeleteUserServlet] Processing POST request");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            System.out.println("Deleting user with ID: " + userId);
            
            UserDAO dao = new UserDAO();
            boolean success = dao.deleteUser(userId);
            
            if (success) {
                out.print("{\"success\":true}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"Failed to delete user.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid user ID.\"}");
        }
    }
}