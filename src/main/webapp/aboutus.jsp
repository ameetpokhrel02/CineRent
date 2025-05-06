<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>About Us</title>
  <link rel="stylesheet" href="css/about.css" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
  <style>
    body {
      margin: 0;
      font-family: 'Poppins', sans-serif;
      background-color: #0f1016;
      color: #fff;
    }
    .navbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1rem 5%;
      background-color: #0f1016;
    }
    .nav-logo {
      font-size: 1.8rem;
      font-weight: 700;
    }
    .brand-red { color: #ff4d6d; }
    .brand-white { color: #ffffff; }
    .nav-links {
      list-style: none;
      display: flex;
      gap: 2rem;
      margin: 0;
      padding: 0;
    }
    .nav-links li a {
      text-decoration: none;
      color: #ffffff;
      font-weight: 500;
      transition: color 0.3s ease;
    }
    .nav-links li a:hover {
      color: #ff9f43;
    }
    .nav-actions {
      display: flex;
      gap: 1rem;
    }
    .btn {
      padding: 0.4rem 1.2rem;
      font-weight: 500;
      border: none;
      border-radius: 20px;
      cursor: pointer;
      transition: all 0.3s ease;
    }
    .login-btn {
      background: transparent;
      color: #ff4d6d;
      border: 1px solid #ff4d6d;
    }
    .login-btn:hover {
      background: #ff4d6d;
      color: #fff;
    }
    .signup-btn {
      background: linear-gradient(45deg, #ff4d6d, #ff9f43);
      color: #fff;
      border: none;
    }
    .signup-btn:hover {
      opacity: 0.9;
    }
    .full-hero-banner {
      background: linear-gradient(to right, #1f1f27, #2b2b38);
      padding: 100px 20px;
      text-align: center;
    }
    .hero-content h1 {
      font-size: 2.8rem;
      font-weight: 700;
      margin-bottom: 1rem;
      color: #ffffff;
    }
    .hero-content p {
      font-size: 1.2rem;
      color: #cccccc;
    }
    .stats-section {
      padding: 80px 20px;
      background-color: #16161f;
    }
    .stats-heading {
      text-align: center;
      margin-bottom: 50px;
    }
    .stats-heading h2 {
      font-size: 2.2rem;
      margin-bottom: 1rem;
    }
    .stats-heading p {
      max-width: 700px;
      margin: 0 auto;
      color: #cccccc;
      font-size: 1rem;
    }
    .stats-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      gap: 2rem;
    }
    .stats-left {
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
    }
    .stat-block {
      background-color: #1e1e2a;
      padding: 20px;
      border-radius: 10px;
      text-align: center;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    }
    .stat-block h3 {
      font-size: 1.8rem;
      color: #ff4d6d;
    }
    .stat-block span {
      color: #ffffff;
      font-size: 1.2rem;
    }
    .stat-block p {
      color: #cccccc;
      margin-top: 0.5rem;
    }
    .stats-right img {
      width: 300px;
      border-radius: 12px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5);
    }
   
.plan-card {
  background-color: #10101a;
  padding: 30px 40px;
  border-radius: 15px;
  max-width: 300px;
  margin: 0 auto;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
}

.plan-card h3 {
  font-size: 1.25rem;
  margin-bottom: 10px;
  color: #ffffff;
}

.price {
  font-size: 2rem;
  color: #ff4d6d;
  font-weight: bold;
  margin-bottom: 20px;
}

.price-duration {
  font-size: 0.9rem;
  color: #bbb;
  margin-left: 5px;
}

.features {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  margin-bottom: 10px;
  font-size: 0.95rem;
  color: #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
}

.check {
  color: #ff4d6d;
  font-weight: bold;
  margin-right: 8px;
}

  </style>
</head>
<body>

<!-- Navbar Section -->
<nav class="navbar">
  <div class="nav-logo">
    <span class="brand-red">Cine</span><span class="brand-white">Rent</span>
  </div>

  <ul class="nav-links">
    <li><a href="home.jsp">Home</a></li>
    <li><a href="movies.jsp">Movies</a></li>
    <li><a href="#">About Us</a></li>
  </ul>

  <div class="nav-actions">
    <button class="btn login-btn" onclick="location.href='login.jsp'" >Login</button>
    <button class="btn signup-btn" onclick="location.href='register.jsp'">Sign Up</button>
  </div>
</nav>

<!-- Hero Banner -->
<section class="full-hero-banner">
  <div class="hero-content">
    <h1>When you’re here,<br>you’re home.</h1>
    <p>(And so is all your entertainment.)</p>
  </div>
</section>

<!-- Stats Section -->
<section class="stats-section">
  <div class="stats-heading">
    <h2>We’re fans at heart.</h2>
    <p>
      Of movies, shows, music, and well, all things entertainment. So much so that we built an app that brings it all together—
      streaming services, personal media, ratings and watch lists. As beautiful as it is easy-to-use, Plex gives fans everywhere a
      way to discover, save, and enjoy the entertainment they love the most.
    </p>
  </div>

  <div class="stats-container">
    <div class="stats-left">
      <div class="stat-block">
        <h3>25+<span> million</span></h3>
        <p>Global Users</p>
      </div>
      <div class="stat-block">
        <h3>50k+</h3>
        <p>On Demand Titles</p>
      </div>
      <div class="stat-block">
        <h3>600+</h3>
        <p>Live TV Channels</p>
      </div>
    </div>

    <div class="stats-right">
      <img src="/images/about us 1.png" alt="App Preview on Phones">
    </div>
  </div>
</section>



</body>
</html>
    