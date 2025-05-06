package controller;
import DAO.UserDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;

@WebServlet("/Login")
public class UserLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.validateUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }

        } else {
            try {
                request.setAttribute("errorMessage", "❌ Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response); // Forward keeps request attributes
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
