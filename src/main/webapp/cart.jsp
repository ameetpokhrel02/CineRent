<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Shopping Cart - CineRent</title>
  <link rel="stylesheet" href="css/home.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    .cart-container {
      max-width: 1000px;
      margin: 40px auto;
      padding: 30px;
      background: var(--card-bg, #1a1a1a);
      color: var(--foreground, #f0f0f0);
      border-radius: 12px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.4);
    }
    
    .cart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 25px;
      border-bottom: 2px solid var(--border-color, #333);
      padding-bottom: 15px;
    }
    
    .cart-header h1 {
      font-size: 28px;
      margin: 0;
      color: #fff;
    }
    
    .cart-item {
      display: flex;
      align-items: center;
      padding: 20px;
      margin-bottom: 15px;
      border-radius: 8px;
      background: rgba(255, 255, 255, 0.05);
      transition: transform 0.2s, box-shadow 0.2s;
    }
    
    .cart-item:hover {
      transform: translateY(-3px);
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    }
    
    .cart-item img {
      width: 100px;
      height: 150px;
      object-fit: cover;
      margin-right: 25px;
      border-radius: 6px;
      box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
    }
    
    .item-details {
      flex-grow: 1;
    }
    
    .item-details h3 {
      margin-bottom: 8px;
      color: #fff;
      font-size: 18px;
    }
    
    .item-details p {
      color: var(--text-gray, #aaa);
      margin-bottom: 5px;
    }
    
    .item-price {
      font-weight: bold;
      font-size: 18px;
      min-width: 100px;
      text-align: right;
      color: var(--crimson, #ff4d6d);
    }
    
    .remove-btn {
      background: var(--crimson, #ff4d6d);
      color: white;
      border: none;
      padding: 8px 15px;
      border-radius: 6px;
      cursor: pointer;
      margin-left: 15px;
      transition: all 0.3s;
      font-weight: 500;
    }
    
    .remove-btn:hover {
      background: var(--crimson-dark, #e63e5c);
      transform: scale(1.05);
    }
    
    .cart-total {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
      padding-top: 20px;
      border-top: 2px solid var(--border-color);
      font-size: 1.2em;
      font-weight: bold;
    }
    .checkout-btn {
      display: block;
      width: 100%;
      padding: 12px;
      background: var(--crimson);
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 1.1em;
      cursor: pointer;
      margin-top: 20px;
      transition: background 0.3s;
    }
    .checkout-btn:hover {
      background: var(--crimson-dark);
    }
    .empty-cart {
      text-align: center;
      padding: 40px 0;
      color: var(--text-gray);
    }
    .checkout-btn:disabled {
      background: #555;
      cursor: not-allowed;
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
    <div class="search-container">
      <input type="text" id="searchInput" placeholder="Search movies..." class="search-input">
      <button class="search-btn">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><path d="m21 21-4.3-4.3"></path></svg>
      </button>
    </div>
    
    <div class="cart-icon">
      <a href="cart.jsp" title="View Cart">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
        <span id="cart-count" class="cart-count">0</span>
      </a>
    </div>
    
    <div class="nav-actions">
      <button class="btn login-btn" onclick="location.href='login.jsp'">Login</button>
      <button class="btn signup-btn" onclick="location.href='register.jsp'">Sign Up</button>
    </div>
  </nav>

  <div class="cart-container">
    <h1>Your Shopping Cart</h1>
    <div id="cart-items">
      <!-- Cart items will be loaded here dynamically -->
    </div>
    <div id="cart-total" class="cart-total">
      <span>Total:</span>
      <span id="total-amount">$0.00</span>
    </div>
    <button id="checkout-btn" class="checkout-btn">Proceed to Checkout</button>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', async () => {
      try {
        await loadCartItems();
        updateCartCount(); // Uncomment this line
        
        // Setup checkout button
        document.getElementById('checkout-btn').addEventListener('click', async () => {
          try {
            const response = await fetch('CheckoutServlet', {
              method: 'POST'
            });
            
            const result = await response.json();
            
            if (result.success) {
              alert('Purchase completed successfully!');
              window.location.href = 'purchase-confirmation.jsp';
            } else {
              console.error('Checkout failed:', result.error);
              if (result.error === 'Authentication required') {
                if (confirm('You need to be logged in to complete your purchase. Go to login page?')) {
                  window.location.href = 'login.jsp';
                }
              } else if (result.error === 'Unauthorized role') {
                alert('Checkout failed: ' + result.error + '. Only regular users can make purchases.');
              } else {
                alert('Checkout failed: ' + result.error);
              }
            }
          } catch (error) {
            console.error('Error during checkout:', error);
            alert('An error occurred during checkout. Please try again later.');
          }
        });
      } catch (error) {
        console.error('Error initializing cart page:', error);
        document.getElementById('cart-items').innerHTML = '<div class="empty-cart"><h3>Error loading cart</h3><p>There was a problem loading your cart. Please try refreshing the page.</p></div>';
      }
    });

    // Add the missing updateCartCount function
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

    async function loadCartItems() {
      try {
        const response = await fetch('CartServlet');
        const cartItems = await response.json();
        
        const cartItemsElement = document.getElementById('cart-items');
        const totalAmountElement = document.getElementById('total-amount');
        const checkoutButton = document.getElementById('checkout-btn');
        
        if (cartItems.length === 0) {
          cartItemsElement.innerHTML = `
            <div class="empty-cart">
              <h3>Your cart is empty</h3>
              <p>Looks like you haven't added any movies to your cart yet.</p>
              <a href="movies.jsp" class="continue-shopping">Browse Movies</a>
            </div>
          `;
          totalAmountElement.textContent = '$0.00';
          checkoutButton.disabled = true;
          return;
        }
        
        let total = 0;
        let cartItemsHTML = '';
        
        cartItems.forEach(item => {
          // Parse the price as a number
          const itemPrice = typeof item.price === 'string' ? parseFloat(item.price.replace('$', '')) : Number(item.price);
          total += itemPrice;
          
          cartItemsHTML += `
            <div class="cart-item" data-id="${item.id}">
              <img src="${item.posterPath || 'images/movie-placeholder.jpg'}" alt="${item.title}">
              <div class="item-details">
                <h3>${item.title}</h3>
                <p>Format: Digital Rental</p>
                <p>Duration: 48-hour access</p>
              </div>
              <div class="item-price">$${itemPrice.toFixed(2)}</div>
              <button class="remove-btn" onclick="removeFromCart(${item.id})">Remove</button>
            </div>
          `;
        });
        
        cartItemsElement.innerHTML = cartItemsHTML;
        totalAmountElement.textContent = '$' + total.toFixed(2);
        checkoutButton.disabled = false;
      } catch (error) {
        console.error('Error loading cart items:', error);
        document.getElementById('cart-items').innerHTML = '<div class="empty-cart"><h3>Error loading cart</h3><p>There was a problem loading your cart. Please try refreshing the page.</p></div>';
      }
    }

    async function removeFromCart(movieId) {
      try {
        const response = await fetch('RemoveFromCartServlet', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ movieId: movieId })
        });
        
        if (response.ok) {
          await loadCartItems();
          updateCartCount(); // Update cart count after removing item
        } else {
          alert('Failed to remove item from cart');
        }
      } catch (error) {
        console.error('Error:', error);
        alert('An error occurred');
      }
    }
  </script>
</body>
</html>






