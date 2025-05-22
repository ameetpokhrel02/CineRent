<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
// Check if user is logged in
User currentUser = (User) session.getAttribute("user");
if (currentUser != null) {
    System.out.println("Home.jsp: User is logged in - " + currentUser.getUsername() + ", Role: " + currentUser.getRole());
} else {
    System.out.println("Home.jsp: No user logged in");
}
%>
    
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>CineRent - Watch Movies Online</title>
    <meta name="description" content="Watch the latest movies and TV shows online with Crimson Cinema" />
    <meta name="author" content="Crimson Cinema" />

    <link rel="stylesheet" href="css/home.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  </head>

  <body>
    <nav class="navbar">
      <div class="container">
        <a href="home.jsp" class="logo">
          <span class="logo-crimson">Crimson</span>
          <span class="logo-cinema">Cinema</span>
        </a>
        
        <div class="nav-links" id="navLinks">
          <a href="home.jsp" class="nav-link">Home</a>
          <a href="movies.jsp" class="nav-link">Movies</a>
          <a href="movies.jsp" class="nav-link">Pricing</a>
          <a href="aboutus.jsp" class="nav-link">About Us</a>
        </div>
        
        
        
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
    <button class="btn login-btn" onclick="location.href='login.jsp'" >Login</button>
    <button class="btn signup-btn" onclick="location.href='register.jsp'">Sign Up</button>
  </div>
      </div>
      
      
      
    </nav>

    <!-- Hero Section with Slider -->
    <section class="hero-slider" id="heroSlider">
      <div class="hero-slider-container" id="heroSliderContainer">
        <!-- Will be filled by JavaScript -->
      </div>
      
      <div class="slider-navigation">
        <button id="prevSlide" class="slider-nav-btn prev-btn">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="15 18 9 12 15 6"></polyline></svg>
        </button>
        <div class="slider-dots" id="sliderDots">
          <!-- Will be filled by JavaScript -->
        </div>
        <button id="nextSlide" class="slider-nav-btn next-btn">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="9 18 15 12 9 6"></polyline></svg>
        </button>
      </div>
    </section>
    
    <main class="container">
      <!-- Popular Movies -->
      <section class="movie-section">
        <div class="section-header">
          <h2 class="section-title">Popular Movies<span class="dot">.</span></h2>
          <p class="section-subtitle">Discover the most watched movies of the week</p>
        </div>
        <div class="movie-grid" id="popularMovies">
          <!-- Will be filled by JavaScript -->
        </div>
      </section>
      
      <!-- New Releases -->
      <section class="movie-section">
        <div class="section-header">
          <h2 class="section-title">New Releases<span class="dot">.</span></h2>
          <p class="section-subtitle">The latest movies added to our collection</p>
        </div>
        <div class="movie-grid" id="newReleases">
          <!-- Will be filled by JavaScript -->
        </div>
      </section>
      
      <!-- Top Rated -->
      <section class="movie-section">
        <div class="section-header">
          <h2 class="section-title">Top Rated<span class="dot">.</span></h2>
          <p class="section-subtitle">Movies with the highest ratings from our users</p>
        </div>
        <div class="movie-grid" id="topRated">
        </div>
      </section>
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
              <li><a href="movies.jsp">Movies</a></li>
              <li><a href="movies.jsp">Pricing</a></li>
              <li><a href="aboutus.jsp">About US</a><li>
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
