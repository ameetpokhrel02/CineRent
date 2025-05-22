<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Orders - CineRent</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    :root {
      --background: #121212;
      --card-bg: #1e1e1e;
      --text-primary: #ffffff;
      --text-secondary: #b3b3b3;
      --accent: #ea384c;
      --accent-hover: #c8303f;
      --border-color: #333;
    }
    
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    
    body {
      font-family: 'Poppins', sans-serif;
      background-color: var(--background);
      color: var(--text-primary);
      line-height: 1.6;
    }
    
    .container {
      width: 100%;
      max-width: 1280px;
      margin: 0 auto;
      padding: 0 1rem;
    }
    
    /* Navbar styles */
    .navbar {
      background-color: #000;
      padding: 1rem 0;
      position: sticky;
      top: 0;
      z-index: 100;
      border-bottom: 1px solid var(--border-color);
    }
    
    /* Orders container */
    .orders-container {
      max-width: 900px;
      margin: 40px auto;
      padding: 30px;
      background: var(--card-bg);
      border-radius: 12px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
    }
    
    .orders-container h1 {
      font-size: 28px;
      margin-bottom: 30px;
      color: var(--text-primary);
      text-align: center;
      border-bottom: 1px solid var(--border-color);
      padding-bottom: 15px;
    }
    
    /* Order styles */
    .order {
      margin-bottom: 30px;
      padding: 20px;
      border-radius: 8px;
      background-color: rgba(255, 255, 255, 0.05);
      border: 1px solid var(--border-color);
    }
    
    .order-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 1px solid var(--border-color);
    }
    
    .order-id {
      font-weight: 600;
      color: var(--accent);
      font-size: 18px;
    }
    
    .order-date {
      font-weight: 500;
      color: var(--text-secondary);
    }
    
    .order-items {
      margin-bottom: 15px;
    }
    
    .order-item {
      display: flex;
      justify-content: space-between;
      padding: 10px 0;
      border-bottom: 1px dashed rgba(255, 255, 255, 0.1);
    }
    
    .item-name {
      flex: 2;
      color: var(--text-primary);
    }
    
    .item-price {
      flex: 1;
      text-align: right;
      color: var(--text-secondary);
    }
    
    .order-total {
      text-align: right;
      font-weight: 600;
      font-size: 1.1em;
      margin-top: 15px;
      color: var(--text-primary);
      background-color: rgba(255, 255, 255, 0.05);
      padding: 10px;
      border-radius: 4px;
    }
    
    .empty-orders {
      text-align: center;
      padding: 40px 0;
      color: var(--text-secondary);
    }
    
    .action-buttons {
      margin-top: 30px;
      text-align: center;
    }
    
    .btn {
      display: inline-block;
      padding: 12px 24px;
      background-color: var(--accent);
      color: white;
      border: none;
      border-radius: 6px;
      font-weight: 600;
      cursor: pointer;
      transition: background-color 0.3s;
      text-decoration: none;
    }
    
    .btn:hover {
      background-color: var(--accent-hover);
    }
    
    .loading {
      text-align: center;
      padding: 40px 0;
      color: var(--text-secondary);
    }
  </style>
</head>
<body>
  <!-- Navigation -->
  <nav class="navbar">
    <div class="nav-brand">
      <span class="brand-red">Cine</span><span class="brand-white">Rent</span>
    </div>
    <ul class="nav-menu">
      <li><a href="home.jsp">Home</a></li>
      <li><a href="movies.jsp">Movies</a></li>
      <li><a href="#">Pricing</a></li>
      <li><a href="aboutus.jsp">About Us</a></li>
    </ul>
    <div class="nav-actions">
      <a href="cart.jsp" class="cart-icon">
        <span id="cart-count">0</span>
      </a>
      <div id="user-menu">
        <!-- Will be populated by JavaScript -->
      </div>
    </div>
  </nav>

  <div class="orders-container">
    <h1>Your Order History</h1>
    <div id="orders-list" class="loading">
      <p>Loading your orders...</p>
    </div>
    
    <div class="action-buttons">
      <button class="btn" onclick="location.href='home.jsp'">Continue Shopping</button>
    </div>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', async () => {
      try {
        // Check if user is logged in
        const userResponse = await fetch('CheckAuthServlet');
        const userData = await userResponse.json();
        
        if (!userData.authenticated) {
          // Redirect to login page if not logged in
          window.location.href = 'login.jsp';
          return;
        }
        
        // Update user menu
        updateUserMenu(userData);
        
        // Update cart count
        updateCartCount();
        
        // Load orders
        await loadOrders();
      } catch (error) {
        console.error('Error initializing page:', error);
        document.getElementById('orders-list').innerHTML = '<p>Error loading orders. Please try again later.</p>';
      }
    });
    
    async function updateUserMenu(userData) {
      const userMenu = document.getElementById('user-menu');
      
      if (userData.authenticated) {
        userMenu.innerHTML = `
          <button class="btn login-btn" onclick="location.href='user-orders.jsp'">My Orders</button>
          <button class="btn signup-btn" onclick="location.href='LogoutServlet'">Logout</button>
        `;
      } else {
        userMenu.innerHTML = `
          <button class="btn login-btn" onclick="location.href='login.jsp'">Login</button>
          <button class="btn signup-btn" onclick="location.href='register.jsp'">Sign Up</button>
        `;
      }
    }
    
    async function updateCartCount() {
      try {
        const response = await fetch('CartServlet');
        const cartItems = await response.json();
        
        const cartCountElement = document.getElementById('cart-count');
        if (cartCountElement) {
          cartCountElement.textContent = cartItems.length;
        }
      } catch (error) {
        console.error('Error updating cart count:', error);
      }
    }
    
    async function loadOrders() {
      try {
        const response = await fetch('OrdersServlet');
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const orders = await response.json();
        const ordersListElement = document.getElementById('orders-list');
        
        if (orders.error) {
          if (orders.error === 'Authentication required') {
            window.location.href = 'login.jsp';
            return;
          }
          ordersListElement.innerHTML = `<p>Error: ${orders.error}</p>`;
          return;
        }
        
        if (orders.length === 0) {
          ordersListElement.innerHTML = `
            <div class="empty-orders">
              <h3>You haven't placed any orders yet</h3>
              <p>Browse our collection and start shopping!</p>
            </div>
          `;
          return;
        }
        
        // Sort orders by date (newest first)
        orders.sort((a, b) => new Date(b.date) - new Date(a.date));
        
        let ordersHTML = '';
        
        orders.forEach((order, index) => {
          const orderDate = new Date(order.date).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
          });
          
          let itemsHTML = '';
          
          order.items.forEach(item => {
            itemsHTML += `
              <div class="order-item">
                <div class="item-name">${item.title}</div>
                <div class="item-price">$${item.price.toFixed(2)}</div>
              </div>
            `;
          });
          
          ordersHTML += `
            <div class="order">
              <div class="order-header">
                <div class="order-id">Order #${order.id}</div>
                <div class="order-date">${orderDate}</div>
              </div>
              <div class="order-items">
                ${itemsHTML}
              </div>
              <div class="order-total">
                Total: $${order.total.toFixed(2)}
              </div>
            </div>
          `;
        });
        
        ordersListElement.innerHTML = ordersHTML;
      } catch (error) {
        console.error('Error loading orders:', error);
        document.getElementById('orders-list').innerHTML = '<p>Error loading orders. Please try again later.</p>';
      }
    }
  </script>
</body>
</html>
