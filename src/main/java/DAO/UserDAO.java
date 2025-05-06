package DAO;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	
	private Connection connection;
	public UserDAO() {
		try {
			
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            connection = DriverManager.getConnection(
            	    "jdbc:mysql://127.0.0.1:3306/users?useSSL=false", "root", "");


        } catch (Exception e) {
            e.printStackTrace();
            
            throw new RuntimeException("Failed to connect to the database", e);
        }
	}
	
	public boolean insertUser(User user) {
		
		String sql="Insert into users (username,email,password,first_name,last_name,phone,profile_image,role) values(?,?,?,?,?,?,?,?)";
		
		try (PreparedStatement ps=connection.prepareStatement(sql)){
			System.out.print("sth");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getFirst_name());
			ps.setString(5, user.getLast_name());
			ps.setInt(6, user.getPhone());
			ps.setBytes(7, user.getProfile_image());
			ps.setString(8, user.getRole());
			
			int rowsInserted = ps.executeUpdate();
			return rowsInserted > 0;
		}
		
	catch(Exception e){
		e.printStackTrace();
		return false;}
				}

		
	public User validateUser(String email, String password) {
	    String sql = "SELECT * FROM users WHERE email=? AND password=?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, email);
	        ps.setString(2, password);

	        var rs = ps.executeQuery();
	        if (rs.next()) {
	            return new User(
	                rs.getInt("id"),
	                rs.getString("username"),
	                rs.getString("email"),
	                rs.getString("password"),
	                rs.getString("first_name"),
	                rs.getString("last_name"),
	                rs.getInt("phone"),
	                rs.getBytes("profile_image"),
	                rs.getString("role")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}


}
