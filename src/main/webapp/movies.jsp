<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cine Rent</title>
  <link rel="stylesheet" href="css/ha.css">
</head>
<body>
 

  <!-- Navigation -->
  <nav class="navbar">
    <div class="nav-brand">
      <span class="brand-red">Cine</span><span class="brand-white">Rent</span>
    </div>
  <ul class="nav-menu">
    <li><a href="home.jsp">Home</a></li>
    <li><a href="#">Movies</a></li>
    <li><a href="#">Pricing</a></li>
    <li><a href="aboutus.jsp">About Us</a></li>
  </ul>
  <div class="nav-actions">
    <button class="btn login-btn" onclick="location.href='login.jsp'" >Login</button>
    <button class="btn signup-btn" onclick="location.href='register.jsp'">Sign Up</button>
  </div>
</nav>

  <div class="hero-carousel">
    <div class="hero-slides">
      
      <div class="hero-slide">
        <img src="images/peaky blinders.jpg" alt="Banner 1">
        <div class="hero-text">
          <h1>Free Movies to Watch, Anytime Anywhere.</h1>
          <p>The search is over! Let Plex help you find the perfect movie to watch tonight for free.</p>
          <a href="#" class="hero-btn">Start Watching</a>
        </div>
      </div>
  
      <div class="hero-slide">
        <img src="images/worst of evil.webp" alt="Banner 2">
        <div class="hero-text">
        <h1>Free Movies to Watch, Anytime Anywhere.</h1>
        <p>The search is over! Let Plex help you find the perfect movie to watch tonight for free.</p>
        <a href="#" class="hero-btn">Start Watching</a>
      </div>
      </div>
  
      <div class="hero-slide">
        <img src="images/slide img1.jpg" alt="Banner 3">
        <div class="hero-text">
        <h1>Free Movies to Watch, Anytime Anywhere.</h1>
          <p>The search is over! Let Plex help you find the perfect movie to watch tonight for free.</p>
          <a href="#" class="hero-btn">Start Watching</a>
        </div>
      </div>
  
    </div>
  </div>
  
  

  <!-- New Releases -->
  <section class="releases">
    <h3>New And Upcoming Releases At MoviesUnlimited.Com</h3>
    <div class="movie-list">
      <a href="movie1.jsp" class="movie-link">
        <div class="movie">
          <img src="images/Inspector Ellis Series 1 (2024)" alt="Inspector Ellis">
          <p class="quick-view">QUICK VIEW</p>
          <p>Inspector Ellis: Series 1<br><span class="type">DVD</span></p>
          <p class="price"><del>$39.99</del> Now $31.99</p>
          <button class="preorder">PRE-ORDER</button>
        </div>
      </a>
      
      <a href="movie2.jsp" class="movie-link">
        <div class="movie">
          <img src="images/terminator judge.png" alt="Terminator 2: Judgment Day">
          <p class="quick-view">QUICK VIEW</p>
          <p>Terminator 2: Judgment Day (1991)<br><span class="type">Blu-ray</span></p>
          <p class="price"><del>$14.99</del> Now $10.99</p>
          <button class="preorder">PRE-ORDER</button>
        </div>
      </a>
      
      <a href="movie3.jsp" class="movie-link">
        <div class="movie">
          <img src="images/avengers.jpg" alt="Avengers: Endgame (2019)">
          <p class="quick-view">QUICK VIEW</p>
          <p>Avengers: Endgame (2019)<br><span class="type">4K Ultra HD</span></p>
          <p class="price"><del>$39.95</del> Now $31.99</p>
          <button class="preorder">PRE-ORDER</button>
        </div>
      </a>
      
      <a href="movie4.jsp" class="movie-link">
      <div class="movie">
        <img src="images/pirates.jpeg" alt="Pirates of the Caribbean: Dead Man's Chest">
        <p class="quick-view">QUICK VIEW</p>
        <p>
            Pirates of the Caribbean: Dead Man's Chest<br><span class="type">4K Ultra HD</span></p>
        <p class="price"><del>$49.95</del> Now $39.99</p>
        <button class="add-to-cart">PRE-ORDER</button>
      </div>
    </a>
    <a href="movie5.jsp" class="movie-link">
      <div class="movie">
        <img src="images/Johnny_English_Strikes_Again_poster.jpg" alt="Johnny English Strikes Again">
        <p class="quick-view">QUICK VIEW</p>
        <p>Johnny English Strikes Again<br><span class="type">DVD</span></p>
        <p class="price"><del>$29.95</del> Now $23.99</p>
        <button class="preorder">PRE-ORDER</button>
      </div>
    </a>
    </div>
  </section>
  
  <section class="fan-favorites">
    <div class="favorites-header">
      <h2>Fan Favorites For You</h2>
      <a href="#">Complete List</a>
    </div>
  
    <div class="favorites-carousel">
      <button class="arrow left">&#10094;</button>
  
      <div class="favorites-list">
        <!-- Movie 1 -->
        <a href="movie6.jsp" class="movie-link">
        <div class="movie">
          <p class="quick-view">QUICK VIEW</p>
          <img src="images/background.jpeg" alt="Titanic">
          <p><strong>Titanic</strong></p>
          <p>DVD</p>
          <p><del>$29.95</del> <span class="price">Now $22.99</span></p>
          <button class="add-to-cart">ADD TO CART</button>
        </div>
        </a>
  
        <!-- Movie 2 -->
        <a href="movie7.jsp" class="movie-link">
        <div class="movie">
          <p class="quick-view">QUICK VIEW</p>
          <img src="images/walking dead.png" alt="Fear the Walking Dead">
          <p><strong>Fear the Walking Dead: The Final Season</strong></p>
          <p>DVD</p>
          <p><del>$38.98</del> <span class="price">Now $33.99</span></p>
          <button class="add-to-cart">ADD TO CART</button>
        </div>
        </a>
  
        <!-- Movie 3 -->
        <a href="movie8.jsp" class="movie-link">
        <div class="movie">
          <p class="quick-view">QUICK VIEW</p>
          <img src="images/guardian.png" alt="Guardians of the Galaxy">
          <p><strong>Guardians of the Galaxy</strong></p>
          <p>DVD</p>
          <p><del>$19.99</del> <span class="price">Now $17.99</span></p>
          <button class="add-to-cart">ADD TO CART</button>
        </div>
      </a>
  
        <!-- Movie 4 -->
        <a href="movie9.jsp" class="movie-link">
        <div class="movie">
          <p class="quick-view">QUICK VIEW</p>
          <img src="images/natural born.png" alt="Natural Born Killers">
          <p><strong>Natural Born Killers</strong></p>
          <p>4K Ultra HD</p>
          <p><del>$52.99</del> <span class="price">Now $40.99</span></p>
          <button class="add-to-cart">ADD TO CART</button>
        </div>
      </a>
  
        <!-- Movie 5 -->
        <a href="movie10.jsp" class="movie-link">
        <div class="movie">
          <p class="quick-view">QUICK VIEW</p>
          <img src="images/death in paradise.png" alt="Death in Paradise">
          <p><strong>Death in Paradise: Season Eleven</strong></p>
          <p>DVD</p>
          <p><del>$39.99</del> <span class="price">Now $34.99</span></p>
          <button class="add-to-cart">ADD TO CART</button>
        </div>
      </a>
      </div>
  
      <button class="arrow right">&#10095;</button>
    </div>
  </section>

  <section class="pricing-footer">
    <div class="pricing-container">
      <h2>Choose Your Perfect Plan</h2>
      <p class="subtext">
        Unlock premium entertainment with our flexible subscription plans.
        No hidden fees, cancel anytime.
      </p>
  
      <div class="plan-card">
        <h3>Basic Plan</h3>
        <p class="price">
          <span class="price-amount">$7.99</span><span class="price-duration">/month</span>
        </p>
        <ul class="features">
          <li><span class="check">✔</span> HD Streaming</li>
          <li><span class="check">✔</span> Watch on 1 Device</li>
        </ul>
      </div>
    </div>
  </section>
  
  

  
</body>
</html>