// Will be populated from the server
let movies = [];

// Update cart count
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

// DOM Elements
document.addEventListener('DOMContentLoaded', () => {
  // Set current year in footer
  document.getElementById('currentYear').textContent = new Date().getFullYear();

  // Update cart count
  updateCartCount();

  // Check if we're on the home page or movie details page
  const isMovieDetails = window.location.pathname.includes('movie-details.jsp');

  if (isMovieDetails) {
    // Movie details page
    loadMovieDetails();
  } else {
    // Home page
    loadHomePage();
  }
});

// Home Page Functions

async function loadHomePage() {
  try {
    // Fetch movies from the server
    const response = await fetch('HomeMovieServlet');
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    movies = await response.json();
    console.log('Loaded movies from server:', movies.length);

    if (movies.length === 0) {
      console.error('No movies returned from server');
      return;
    }

    // Render hero slider (using first 5 movies from the array)
    const sliderMovies = movies.slice(0, Math.min(5, movies.length));
    renderHeroSlider(sliderMovies);

    // Render movie grids
    renderMovieGrid('popularMovies', movies);
    renderMovieGrid('newReleases', [...movies].reverse().slice(0, Math.min(5, movies.length)));
    renderMovieGrid('topRated', [...movies].sort((a, b) => b.voteAverage - a.voteAverage).slice(0, Math.min(5, movies.length)));
  } catch (error) {
    console.error('Error loading movies:', error);
    // Display error message to user
    const main = document.querySelector('main');
    if (main) {
      main.innerHTML = `
        <div class="error-container">
          <h2>Error Loading Movies</h2>
          <p>Sorry, we couldn't load the movies. Please try again later.</p>
          <p>Error details: ${error.message}</p>
        </div>
      `;
    }
  }
}

function renderHeroSlider(sliderMovies) {
  const sliderContainer = document.getElementById('heroSliderContainer');
  const dotsContainer = document.getElementById('sliderDots');

  // Create slides
  sliderMovies.forEach((movie, index) => {
    const slide = document.createElement('div');
    slide.className = 'hero-slide';
    slide.style.backgroundImage = `url(${movie.backdropPath})`;

    const content = document.createElement('div');
    content.className = 'hero-content';

    content.innerHTML = `
      <h1 class="hero-title">${movie.title}</h1>
      <div class="hero-info">
        <span>${movie.releaseDate.split('-')[0]}</span>
        <span class="dot-divider"></span>
        <span>${movie.runtime} min</span>
        <span class="dot-divider"></span>
        <span>${movie.genres.join(', ')}</span>
      </div>
      <p class="hero-overview">${movie.overview}</p>
      <div class="hero-buttons">
        <a href="movie-details.jsp?id=${movie.id}" class="btn btn-primary">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polygon points="5 3 19 12 5 21 5 3"></polygon></svg>
          Watch Now
        </a>
        <a href="movie-details.jsp?id=${movie.id}" class="btn btn-secondary">Buy Now</a>
      </div>
    `;

    slide.appendChild(content);
    sliderContainer.appendChild(slide);

    // Create dot for this slide
    const dot = document.createElement('div');
    dot.className = `slider-dot ${index === 0 ? 'active' : ''}`;
    dot.dataset.index = index;
    dot.addEventListener('click', () => {
      currentSlide = index;
      updateSlider();
    });
    dotsContainer.appendChild(dot);
  });

  // Set up slider functionality
  let currentSlide = 0;

  const updateSlider = () => {
    sliderContainer.style.transform = `translateX(-${currentSlide * 100}%)`;

    // Update dots
    document.querySelectorAll('.slider-dot').forEach((dot, index) => {
      if (index === currentSlide) {
        dot.classList.add('active');
      } else {
        dot.classList.remove('active');
      }
    });
  };

  // Next/Prev button functionality
  document.getElementById('nextSlide').addEventListener('click', () => {
    currentSlide = (currentSlide + 1) % sliderMovies.length;
    updateSlider();
  });

  document.getElementById('prevSlide').addEventListener('click', () => {
    currentSlide = (currentSlide - 1 + sliderMovies.length) % sliderMovies.length;
    updateSlider();
  });

  // Auto-advance the slider (optional)
  setInterval(() => {
    currentSlide = (currentSlide + 1) % sliderMovies.length;
    updateSlider();
  }, 5000);
}

function renderMovieGrid(containerId, moviesList) {
  const container = document.getElementById(containerId);
  container.innerHTML = '';

  moviesList.forEach(movie => {
    const movieCard = createMovieCard(movie);
    container.appendChild(movieCard);
  });
}

function createMovieCard(movie) {
  const movieCard = document.createElement('a');
  movieCard.href = `movie-details.jsp?id=${movie.id}`;
  movieCard.className = 'movie-card';

  movieCard.innerHTML = `
    <div class="movie-poster">
      <img src="${movie.posterPath}" alt="${movie.title}">
      <div class="movie-rating">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon></svg>
        ${movie.voteAverage.toFixed(1)}
      </div>
    </div>
    <div class="movie-info">
      <h3 class="movie-title">${movie.title}</h3>
      <p class="movie-meta">${movie.releaseDate.split('-')[0]} • ${movie.genres.slice(0, 2).join(', ')}</p>
    </div>
  `;

  return movieCard;
}

// Movie Details Page Functions
async function loadMovieDetails() {
  // Get movie ID from URL
  const urlParams = new URLSearchParams(window.location.search);
  const movieId = parseInt(urlParams.get('id'));

  if (!movieId) {
    window.location.href = 'home.jsp';
    return;
  }

  try {
    // If movies array is empty, fetch movies from server
    if (movies.length === 0) {
      const response = await fetch('HomeMovieServlet');
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      movies = await response.json();
    }

    const movie = movies.find(m => m.id === movieId);

    if (!movie) {
      window.location.href = 'home.jsp';
      return;
    }

  // Render movie details
  document.title = `${movie.title} - Crimson Cinema`;

  const main = document.querySelector('main');
  main.className = 'movie-details';

  // Create backdrop header
  const backdropHeader = document.createElement('div');
  backdropHeader.className = 'backdrop-header';
  backdropHeader.style.backgroundImage = `url(${movie.backdropPath})`;
  document.body.insertBefore(backdropHeader, main);

  // Create movie content
  main.innerHTML = `
    <div class="container">
      <div class="movie-content">
        <div class="movie-content-grid">
          <div class="movie-poster-container">
            <div class="movie-poster-lg">
              <img src="${movie.posterPath}" alt="${movie.title}">
            </div>

            <div class="movie-details-card">
              <div class="movie-details-card-row">
                <span class="detail-label">Rating</span>
                <span>
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="gold" stroke="gold" stroke-width="1" stroke-linecap="round" stroke-linejoin="round">
                    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
                  </svg>
                  ${movie.voteAverage.toFixed(1)}/10
                </span>
              </div>
              <div class="movie-details-card-row">
                <span class="detail-label">Release Date</span>
                <span>${movie.releaseDate}</span>
              </div>
              <div class="movie-details-card-row">
                <span class="detail-label">Runtime</span>
                <span>${movie.runtime} min</span>
              </div>
              <div class="movie-details-card-row">
                <span class="detail-label">Genres</span>
                <span>${movie.genres.join(', ')}</span>
              </div>
			  <button id="buyButton" class="btn btn-buy">Buy for $9.99</button>
            </div>
		  </div>

          <div>
            <h1 class="movie-title-lg">${movie.title}</h1>

            ${movie.videoUrl ? `
              <div class="video-container">
                <div class="video-wrapper">
                  <iframe src="${movie.videoUrl}" title="${movie.title}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </div>
              </div>
            ` : ''}

            <div class="overview-section">
              <h2 class="overview-title">Overview</h2>
              <p>${movie.overview}</p>


            </div>


            <div class="recommended-section">
              <h2 class="related-title">You May Also Like</h2>
              <div class="movie-grid" id="recommendedMovies"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `;

const buyBtn = document.getElementById('buyButton');
if (buyBtn) {
  buyBtn.addEventListener('click', async (event) => {
    event.preventDefault(); 
    try {
      const response = await fetch('AddToCartServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ movieId: movie.id })
      });

      if (response.ok) {
        alert('Movie added to cart!');
        updateCartCount(); // Update the cart count
        const viewCart = confirm('View cart now?');
        if (viewCart) {
          window.location.href = 'cart.jsp';
        }
      } else {
        alert('Failed to add movie to cart');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred');
    }
  });
} else {
  console.warn('Buy button not found in DOM');
}

  // Render recommended movies (similar genres)
  const recommendedMovies = movies
    .filter(m => m.id !== movieId && m.genres.some(genre => movie.genres.includes(genre)))
    .slice(0, 5);
  
  const recommendedContainer = document.getElementById('recommendedMovies');
  recommendedMovies.forEach(movie => {
    const movieCard = createMovieCard(movie);
    recommendedContainer.appendChild(movieCard);
  });
  } catch (error) {
    console.error('Error loading movie details:', error);
    // Display error message to user
    const main = document.querySelector('main');
    if (main) {
      main.innerHTML = `
        <div class="container">
          <div class="error-container">
            <h2>Error Loading Movie Details</h2>
            <p>Sorry, we couldn't load the movie details. Please try again later.</p>
            <p>Error details: ${error.message}</p>
          </div>
        </div>
      `;
    }
  }
}
