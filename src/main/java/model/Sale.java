package model;

import java.sql.Date;

public class Sale {
    private int id;
    private int movieId;
    private Integer userId;  // Can be null for guest purchases
    private Date purchaseDate;
    private double price;
    
    // Constructors
    public Sale() {
    }
    
    public Sale(int id, int movieId, Integer userId, Date purchaseDate, double price) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Sale [id=" + id + ", movieId=" + movieId + ", userId=" + userId + ", purchaseDate=" + purchaseDate
                + ", price=" + price + "]";
    }
}