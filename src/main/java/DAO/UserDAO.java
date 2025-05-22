package DAO;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class UserDAO {
	
	private Connection connection;
	public UserDAO() {
		try {
			
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/cinerent?useSSL=false", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
            
            throw new RuntimeException("Failed to connect to the database", e);
        }
	}
	
	public boolean insertUser(User user) {
		String sql = "INSERT INTO users (username, email, password, first_name, last_name, phone, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getFirst_name());
			ps.setString(5, user.getLast_name());
			ps.setInt(6, user.getPhone());
			ps.setString(7, user.getRole());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected > 0) {
				// Get the generated ID
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}
				rs.close();
				ps.close();
				return true;
			}
			
			ps.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in insertUser: " + e.getMessage());
			return false;
		}
	}

		
	public User validateUser(String email, String password) {
	    String sql = "SELECT * FROM users WHERE email=?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, email);
	        System.out.println("Checking if email exists: " + email);
	        
	        var rs = ps.executeQuery();
	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            System.out.println("User found. Stored password: " + storedPassword);
	            System.out.println("Provided password hash: " + password);
	            
	            // First try direct comparison
	            if (storedPassword.equals(password)) {
	                System.out.println("Password matched!");
	                return new User(
	                    rs.getInt("id"),
	                    rs.getString("username"),
	                    rs.getString("email"),
	                    storedPassword,
	                    rs.getString("first_name"),
	                    rs.getString("last_name"),
	                    rs.getInt("phone"),
	                    rs.getBytes("profile_image"),
	                    rs.getString("role")
	                );
	            } else {
	                System.out.println("Password mismatch");
	            }
	        } else {
	            System.out.println("No user found with email: " + email);
	        }
	    } catch (Exception e) {
	        System.out.println("Error in validateUser: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				
				// Handle phone as string or int based on your database schema
				try {
					user.setPhone(rs.getInt("phone"));
				} catch (SQLException e) {
					// If phone is stored as a string in the database
					user.setPhone(Integer.parseInt(rs.getString("phone")));
				}
				
				user.setRole(rs.getString("role"));
				
				users.add(user);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in getAllUsers: " + e.getMessage());
		}
		
		return users;
	}

	public boolean updateUser(User user) {
		String sql = "UPDATE users SET username=?, email=?, password=?, first_name=?, last_name=?, phone=?, role=? WHERE id=?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getFirst_name());
			ps.setString(5, user.getLast_name());
			ps.setInt(6, user.getPhone());
			ps.setString(7, user.getRole());
			ps.setInt(8, user.getId());
			
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(int id) {
		String sql = "DELETE FROM users WHERE id=?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int rowsDeleted = ps.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
