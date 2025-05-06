<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Movie Details - Crimson Cinema</title>
    <meta name="description" content="Watch movies online with Crimson Cinema" />
    <meta name="author" content="Crimson Cinema" />

    
    <link rel="stylesheet" href="css/home.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  </head>

  <body>
    <nav class="navbar">
      <div class="container">
        <a href="index.html" class="logo">
          <span class="logo-crimson">Crimson</span>
          <span class="logo-cinema">Cinema</span>
        </a>
        
        <div class="nav-links" id="navLinks">
          <a href="index.html" class="nav-link">Home</a>
          <a href="#" class="nav-link">Movies</a>
          <a href="#" class="nav-link">TV Shows</a>
          <a href="#" class="nav-link">Categories</a>
        </div>
        
        <div class="search-container">
          <input type="text" id="searchInput" placeholder="Search movies..." class="search-input">
          <button class="search-btn">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><path d="m21 21-4.3-4.3"></path></svg>
          </button>
        </div>
      </div>
    </nav>

    <main class="container">
      <!-- Content will be loaded by JavaScript -->
      <div class="loading">
        <div class="spinner"></div>
      </div>
    </main>
    
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-section">
            <h3 class="footer-title">Crimson Cinema</h3>
            <p class="footer-text">
              Your premier destination for watching movies online. Explore our vast collection of films
              across all genres, from action-packed blockbusters to heartwarming dramas.
            </p>
          </div>
          
          <div class="footer-section">
            <h3 class="footer-title">Quick Links</h3>
            <ul class="footer-links">
              <li><a href="home.jsp">Home</a></li>
              <li><a href="#">Movies</a></li>
              <li><a href="#">TV Shows</a></li>
              <li><a href="#">Categories</a></li>
            </ul>
          </div>
          
          <div class="footer-section">
            <h3 class="footer-title">Legal</h3>
            <ul class="footer-links">
              <li><a href="#">Privacy Policy</a></li>
              <li><a href="#">Terms of Service</a></li>
              <li><a href="#">Cookie Policy</a></li>
            </ul>
          </div>
        </div>
        
        <div class="footer-bottom">
          <p>&copy; <span id="currentYear"></span> Crimson Cinema. All rights reserved.</p>
        </div>
      </div>
    </footer>

    <script src="js/home.js"></script>
  </body>
</html>
    