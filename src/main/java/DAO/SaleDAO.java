package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Sale;

public class SaleDAO {
    
    private Connection connection;
    
    public SaleDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cinerent?useSSL=false", "root", "");
            

		}catch(Exception e) {
			e.printStackTrace();

			throw new RuntimeException("Failed to connect to the database",e);
		}
	}
	
	
	
    public boolean addSale(int movieId, Integer userId, Date purchaseDate, double price) {
        String sql = "INSERT INTO sale (movie_id, user_id, purchase_date, price) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            
            // Handle null userId (guest purchase)
            if (userId != null) {
                pstmt.setInt(2, userId);
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            pstmt.setDate(3, purchaseDate);
            pstmt.setDouble(4, price);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding sale: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Sale> getSalesByUserId(int userId) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sale WHERE user_id = ? ORDER BY purchase_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("id"));
                sale.setMovieId(rs.getInt("movie_id"));
                sale.setUserId(rs.getInt("user_id"));
                sale.setPurchaseDate(rs.getDate("purchase_date"));
                sale.setPrice(rs.getDouble("price"));
                sales.add(sale);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting sales: " + e.getMessage());
            e.printStackTrace();
        }
        
        return sales;
    }
}


