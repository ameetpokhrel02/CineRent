package controller;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.User;
import DAO.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;



@MultipartConfig
@WebServlet("/Register")
public class UserRegister extends HttpServlet{
	
	private static final long serialVersionUID= 1L;
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String Password = request.getParameter("password");
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            int  phone = Integer.parseInt(request.getParameter("phone_number"));
            String role = request.getParameter("role");
            

            Part filePart = request.getPart("profile_image");
            byte[] profileImage=null;
            if (filePart !=null) {
            	InputStream inputStream= filePart.getInputStream();
            	profileImage = inputStream.readAllBytes();
            }
            
            User user = new User(id, username, email,Password, firstName, lastName, phone, profileImage, role);
            
            DAO.UserDAO dao=new DAO.UserDAO();
            boolean success=dao.insertUser(user);
            
            
            if (success) {
                response.getWriter().println("Registration successful.");
                response.sendRedirect("login.jsp");
            } else {
                response.getWriter().println("Registration failed.");
            }
            
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error during registration:" + e.getMessage());
		}
	}
}

