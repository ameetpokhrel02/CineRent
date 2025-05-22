package controller;
import DAO.UserDAO;
import util.HashUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;

@WebServlet("/Login")
public class UserLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("✅ Servlet is working!");
		
		String email = request.getParameter("email");
		String rawPassword = request.getParameter("password");
		
		// Try both hashed and raw password for debugging
		String hashedPassword = HashUtil.hash(rawPassword);
		System.out.println("Login attempt: Email=" + email);
		System.out.println("Raw password: " + rawPassword);
		System.out.println("Hashed password: " + hashedPassword);

		UserDAO dao = new UserDAO();
		
		// First try with hashed password
		User user = dao.validateUser(email, hashedPassword);
		
		// If that fails, try with raw password (for debugging only)
		if (user == null) {
			System.out.println("Trying with raw password...");
			user = dao.validateUser(email, rawPassword);
		}
		
		System.out.println("Login result: " + (user != null ? "Success" : "Failed"));

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("role", user.getRole());
			
			System.out.println("Login successful: User ID=" + user.getId() + ", Username=" + user.getUsername() + ", Role=" + user.getRole());
			System.out.println("Session ID: " + session.getId());

			if ("admin".equalsIgnoreCase(user.getRole())) {
				System.out.println("Redirecting to dashboard.jsp");
				response.sendRedirect("dashboard.jsp");
			} else {
				System.out.println("Redirecting to home.jsp");
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
