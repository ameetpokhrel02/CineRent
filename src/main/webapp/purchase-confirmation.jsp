<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Purchase Confirmation - CineRent</title>
  <link rel="stylesheet" href="css/purchase-confirmation.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
  <!-- Navbar can be included here -->
  
  <div class="confirmation-container">
    <div class="success-icon">
      <i class="fas fa-check-circle"></i>
    </div>
    
    <h1 class="confirmation-title">Thank You for Your Purchase!</h1>
    <p class="confirmation-message">Your order has been successfully processed. You can now enjoy your movie.</p>
    
    <div class="order-details">
      <!-- Order details can be dynamically populated here -->
      <p><strong>Order ID:</strong> <%= request.getAttribute("orderId") != null ? request.getAttribute("orderId") : "ORD-" + System.currentTimeMillis() %></p>
      <p><strong>Date:</strong> <%= new java.text.SimpleDateFormat("MMM dd, yyyy").format(new java.util.Date()) %></p>
      <p><strong>Payment Method:</strong> Credit Card</p>
    </div>
    
    <div class="button-group">
      <a href="movies.jsp" class="btn btn-primary">Continue Shopping</a>
      <a href="user-orders.jsp" class="btn btn-secondary">View My Orders</a>
    </div>
  </div>
</body>
</html>

