// Sample data
let users = [];

let movies = [];

let activities = [
    { action: 'User added', target: 'johndoe', timestamp: new Date(Date.now() - 3600000) },
    { action: 'Movie added', target: 'Inception', timestamp: new Date(Date.now() - 7200000) }
];

// DOM Elements
const navItems = document.querySelectorAll('.nav-item');
const contentSections = document.querySelectorAll('.content-section');
const usersTable = document.getElementById('usersTable');
const moviesTable = document.getElementById('moviesTable');
const userModal = document.getElementById('userModal');
const movieModal = document.getElementById('movieModal');
const deleteModal = document.getElementById('deleteModal');
const userForm = document.getElementById('userForm');
const movieForm = document.getElementById('movieForm');
const addUserBtn = document.getElementById('addUserBtn');
const addMovieBtn = document.getElementById('addMovieBtn');
const closeModalBtns = document.querySelectorAll('.close-modal');
const cancelBtns = document.querySelectorAll('.cancel-btn');
const confirmDeleteBtn = document.getElementById('confirmDelete');
const searchInput = document.getElementById('searchInput');
const userCount = document.getElementById('userCount');
const movieCount = document.getElementById('movieCount');
const activityList = document.getElementById('activityList');

document.addEventListener('DOMContentLoaded', async () => {
    await fetchUsers(); // Fetch users from the backend
    await fetchMovies(); // Fetch movies from the backend
    updateStats();
    renderActivities();
});

// Fetch movies from the backend
async function fetchMovies() {
    try {
        const response = await fetch('ListMovieServlet'); // Remove the '/CineRent/' prefix
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        movies = data.map(movie => ({
            id: movie.id,
            title: movie.title,
            genre: movie.genre,
            releaseYear: movie.releaseYear,
            rating: parseFloat(movie.rating),
            price: parseFloat(movie.price),
            runtime: movie.runtime,
            youtubeLink: movie.youtubeLink,
            overview: movie.overview || '',
            posterPath: movie.posterPath || ''
        }));
        renderMovies();
    } catch (error) {
        console.error('Failed to fetch movies:', error);
    }
}

// Fetch users from the backend
async function fetchUsers() {
    try {
        const response = await fetch('ListUserServlet');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const text = await response.text();
        console.log("Raw response:", text);
        
        // Try to parse the JSON
        const data = JSON.parse(text);
        
        users = data.map(user => ({
            id: user.id,
            username: user.username,
            email: user.email,
            password: user.password,
            firstName: user.first_name,
            lastName: user.last_name,
            phone: user.phone,
            role: user.role
        }));
        
        console.log("Processed users:", users);
        renderUsers();
    } catch (error) {
        console.error('Failed to fetch users:', error);
    }
}

// Navigation
navItems.forEach(item => {
    item.addEventListener('click', () => {
        // Remove active class from all nav items and content sections
        navItems.forEach(nav => nav.classList.remove('active'));
        contentSections.forEach(section => section.classList.remove('active'));

        // Add active class to clicked nav item and corresponding content section
        item.classList.add('active');
        const targetId = item.getAttribute('data-target');
        document.getElementById(targetId).classList.add('active');
    });
});

// Search functionality
searchInput.addEventListener('input', () => {
    const searchTerm = searchInput.value.toLowerCase();

    // Filter users
    const filteredUsers = users.filter(user =>
        user.username.toLowerCase().includes(searchTerm) ||
        user.email.toLowerCase().includes(searchTerm) ||
        user.firstName.toLowerCase().includes(searchTerm) ||
        user.lastName.toLowerCase().includes(searchTerm)
    );
    renderUsers(filteredUsers);

    // Filter movies
    const filteredMovies = movies.filter(movie =>
        movie.title.toLowerCase().includes(searchTerm) ||
        movie.genre.toLowerCase().includes(searchTerm)
    );
    renderMovies(filteredMovies);
});

// Render Users Table
function renderUsers(data = users) {
    const tbody = usersTable.querySelector('tbody');
    tbody.innerHTML = '';

    data.forEach(user => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.phone}</td>
            <td>${user.role}</td>
            <td>
                <button class="action-btn edit-btn" data-id="${user.id}">
                    <span class="material-icons">edit</span>
                </button>
                <button class="action-btn delete-btn" data-id="${user.id}">
                    <span class="material-icons">delete</span>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });

    // Add event listeners to edit and delete buttons
    document.querySelectorAll('#usersTable .edit-btn').forEach(btn => {
        btn.addEventListener('click', () => editUser(btn.getAttribute('data-id')));
    });

    document.querySelectorAll('#usersTable .delete-btn').forEach(btn => {
        btn.addEventListener('click', () => showDeleteConfirmation('user', btn.getAttribute('data-id')));
    });
}

// Render Movies Table
function renderMovies(data = movies) {
    const tbody = moviesTable.querySelector('tbody');
    tbody.innerHTML = '';

    data.forEach(movie => {
        // Create a shortened overview for display
        const shortOverview = movie.overview ?
            (movie.overview.length > 50 ? movie.overview.substring(0, 50) + '...' : movie.overview) :
            'No overview available';

        // Create a poster preview if available
        const posterPreview = movie.posterPath ?
            `<img src="${movie.posterPath}" alt="${movie.title}" style="width: 50px; height: auto;">` :
            'No poster';

        // Convert price to number if it's a string
        const price = typeof movie.price === 'string' ? parseFloat(movie.price) : movie.price;
        
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${movie.id}</td>
            <td>${movie.title}</td>
            <td>${movie.genre}</td>
            <td>${movie.releaseYear}</td>
            <td>${movie.rating}</td>
            <td>$${isNaN(price) ? '0.00' : price.toFixed(2)}</td>
            <td>${movie.runtime}</td>
            <td title="${movie.overview || ''}">${shortOverview}</td>
            <td>${posterPreview}</td>
            <td>
                <button class="action-btn edit-btn" data-id="${movie.id}">
                    <span class="material-icons">edit</span>
                </button>
                <button class="action-btn delete-btn" data-id="${movie.id}">
                    <span class="material-icons">delete</span>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });

    // Add event listeners to edit and delete buttons
    document.querySelectorAll('#moviesTable .edit-btn').forEach(btn => {
        btn.addEventListener('click', () => editMovie(btn.getAttribute('data-id')));
    });

    document.querySelectorAll('#moviesTable .delete-btn').forEach(btn => {
        btn.addEventListener('click', () => showDeleteConfirmation('movie', btn.getAttribute('data-id')));
    });
}



// Add User
addUserBtn.addEventListener('click', () => {
    document.getElementById('userModalTitle').textContent = 'Add User';
    document.getElementById('userId').value = '';
    userForm.reset();
    showModal(userModal);
});

// Only ONE handler for opening the Add Movie modal
addMovieBtn.addEventListener('click', () => {
    movieForm.reset();
    document.getElementById('movieId').value = '';
    document.getElementById('movieModalTitle').textContent = 'Add Movie';
    showModal(movieModal);
});

// Close modal when clicking the close button or cancel
closeModalBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        movieModal.classList.remove('active');
    });
});
cancelBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        movieModal.classList.remove('active');
    });
});


// Save Movie (Add or Edit)
movieForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const movieId = document.getElementById('movieId').value;
    const movieData = {
        id: movieId ? parseInt(movieId) : null,
        title: document.getElementById('title').value,
        genre: document.getElementById('genre').value,
        releaseYear: parseInt(document.getElementById('releaseYear').value),
        rating: parseFloat(document.getElementById('rating').value),
        price: document.getElementById('price').value, // Keep as string for server
        runtime: document.getElementById('runtime').value,
        youtubeLink: document.getElementById('youtubeLink').value,
        overview: document.getElementById('overview').value,
        posterPath: document.getElementById('posterPath').value
    };

    if (movieId) {
        // Edit existing movie
        try {
            const response = await fetch('UpdateMovieServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(movieData)
            });
            if (!response.ok) {
                throw new Error('Failed to update movie');
            }
            // Update the movie in the local array
            const index = movies.findIndex(m => m.id == movieId);
            if (index !== -1) {
                movies[index] = { ...movies[index], ...movieData };
            }
            renderMovies();
            updateStats();
            addActivity('Movie updated', movieData.title);
            hideModal(movieModal);
        } catch (err) {
            alert('Error updating movie: ' + err.message);
        }
    } else {
        try {
            // Get the base URL dynamically
            const baseUrl = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
            const response = await fetch(`${baseUrl}/AddMovieServlet`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(movieData)
            });
            
            // Check if response is JSON
            const contentType = response.headers.get('content-type');
            if (!contentType || !contentType.includes('application/json')) {
                const text = await response.text();
                console.error('Server returned non-JSON response:', text);
                throw new Error('Server returned an invalid response format');
            }
            
            const data = await response.json();
            
            if (!response.ok || data.error) {
                throw new Error(data.error || 'Failed to add movie');
            }
            
            movies.push(data);
            renderMovies();
            updateStats();
            addActivity('Movie added', data.title);
            hideModal(movieModal);
        } catch (err) {
            console.error('Error adding movie:', err);
            alert('Error adding movie: ' + err.message);
        }
    }
});

// Edit User
function editUser(id) {
    const user = users.find(u => u.id == id);
    if (user) {
        document.getElementById('userModalTitle').textContent = 'Edit User';
        document.getElementById('userId').value = user.id;
        document.getElementById('username').value = user.username;
        document.getElementById('email').value = user.email;
        document.getElementById('password').value = user.password;
        document.getElementById('firstName').value = user.firstName;
        document.getElementById('lastName').value = user.lastName;
        document.getElementById('phone').value = user.phone;
        document.getElementById('profilePic').value = user.profilePic;
        document.getElementById('role').value = user.role;

        showModal(userModal);
    }
}

// Edit Movie
function editMovie(id) {
    const movie = movies.find(m => m.id == id);
    if (movie) {
        document.getElementById('movieModalTitle').textContent = 'Edit Movie';
        document.getElementById('movieId').value = movie.id;
        document.getElementById('title').value = movie.title;
        document.getElementById('genre').value = movie.genre;
        document.getElementById('releaseYear').value = movie.releaseYear;
        document.getElementById('rating').value = movie.rating;
        
        // Handle price as string or number
        document.getElementById('price').value = typeof movie.price === 'string' ? 
            movie.price : movie.price.toString();
            
        document.getElementById('runtime').value = movie.runtime;
        document.getElementById('youtubeLink').value = movie.youtubeLink;
        document.getElementById('overview').value = movie.overview || '';
        document.getElementById('posterPath').value = movie.posterPath || '';

        showModal(movieModal);
    }
}

// Save User
userForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const userId = document.getElementById('userId').value;
    const userData = {
        id: userId ? parseInt(userId) : 0,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        phone: document.getElementById('phone').value,
        role: document.getElementById('role').value
    };

    if (userId) {
        // Edit existing user
        try {
            const response = await fetch('UpdateUserServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
            if (!response.ok) {
                throw new Error('Failed to update user');
            }
            // Update the user in the local array
            const index = users.findIndex(u => u.id == userId);
            if (index !== -1) {
                users[index] = { ...users[index], ...userData };
            }
            renderUsers();
            updateStats();
            addActivity('User updated', userData.username);
            hideModal(userModal);
        } catch (err) {
            alert('Error updating user: ' + err.message);
        }
    } else {
        // Add new user
        try {
            const response = await fetch('AddUserServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
            if (!response.ok) {
                throw new Error('Failed to add user');
            }
            const data = await response.json();
            users.push(data);
            renderUsers();
            updateStats();
            addActivity('User added', data.username);
            hideModal(userModal);
        } catch (err) {
            alert('Error adding user: ' + err.message);
        }
    }
});

// Save Movie
movieForm.addEventListener('submit', async (e) => {
    e.preventDefault(); // Prevent the default form submission behavior

    const movieId = document.getElementById('movieId').value;
    const movieData = {
        title: document.getElementById('title').value,
        genre: document.getElementById('genre').value,
        releaseYear: parseInt(document.getElementById('releaseYear').value),
        rating: parseFloat(document.getElementById('rating').value),
        price: document.getElementById('price').value, // keep as string
        runtime: document.getElementById('runtime').value, // keep as string
        youtubeLink: document.getElementById('youtubeLink').value,
        overview: document.getElementById('overview').value,
        posterPath: document.getElementById('posterPath').value
    };

    if (movieId) {
        // Update existing movie
        await editMovie(parseInt(movieId), movieData);
    } else {
        // Add new movie
        try {
            console.log('Sending movie data:', JSON.stringify(movieData));
            
            const response = await fetch('AddMovieServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(movieData)
            }).catch(error => {
                console.error('Network error:', error);
                throw new Error('Network error: ' + error.message);
            });
            
            console.log('Response status:', response.status);
            
            // Check if response is JSON
            const contentType = response.headers.get('content-type');
            if (!contentType || !contentType.includes('application/json')) {
                const text = await response.text();
                console.error('Server returned non-JSON response:', text);
                throw new Error('Server returned an invalid response format');
            }
            
            const data = await response.json();
            console.log('Response data:', data);
            
            if (!response.ok || data.error) {
                throw new Error(data.error || 'Failed to add movie');
            }
            
            movies.push(data);
            renderMovies();
            updateStats();
            addActivity('Movie added', data.title);
            hideModal(movieModal);
        } catch (err) {
            console.error('Error adding movie:', err);
            alert('Error adding movie: ' + err.message);
        }
    }

    renderMovies();
    updateStats();
    hideModal(movieModal);
});

// Delete Confirmation
function showDeleteConfirmation(type, id) {
    let item;
    if (type === 'user') {
        item = users.find(u => u.id == id);
        document.getElementById('deleteMessage').textContent = `Are you sure you want to delete user "${item.username}"?`;
    } else {
        item = movies.find(m => m.id == id);
        document.getElementById('deleteMessage').textContent = `Are you sure you want to delete movie "${item.title}"?`;
    }

    confirmDeleteBtn.setAttribute('data-type', type);
    confirmDeleteBtn.setAttribute('data-id', id);

    showModal(deleteModal);
}

// Confirm Delete
confirmDeleteBtn.addEventListener('click', async () => {
    const type = confirmDeleteBtn.getAttribute('data-type');
    const id = confirmDeleteBtn.getAttribute('data-id');

    if (type === 'user') {
        try {
            const response = await fetch(`DeleteUserServlet?id=${id}`, {
                method: 'POST'
            });
            if (!response.ok) {
                throw new Error('Failed to delete user');
            }
            const index = users.findIndex(u => u.id == id);
            if (index !== -1) {
                const username = users[index].username;
                users.splice(index, 1);
                addActivity('User deleted', username);
                renderUsers();
            }
        } catch (err) {
            alert('Error deleting user: ' + err.message);
        }
    } else {
        const index = movies.findIndex(m => m.id == id);
        if (index !== -1) {
            const title = movies[index].title;
            movies.splice(index, 1);
            addActivity('Movie deleted', title);
            renderMovies();
        }
    }

    updateStats();
    hideModal(deleteModal);
});

// Modal Functions
function showModal(modal) {
    modal.style.display = 'block';
}

function hideModal(modal) {
    modal.style.display = 'none';
}

// Close modals when clicking the close button or cancel button
closeModalBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const modal = btn.closest('.modal');
        hideModal(modal);
    });
});

cancelBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const modal = btn.closest('.modal');
        hideModal(modal);
    });
});

// Close modal when clicking outside the modal content
window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        hideModal(e.target);
    }
});





// Update dashboard stats
function updateStats() {
    userCount.textContent = users.length;
    movieCount.textContent = movies.length;
}

// Add activity
function addActivity(action, target) {
    const activity = {
        action,
        target,
        timestamp: new Date()
    };

    activities.unshift(activity);

    // Keep only the last 10 activities
    if (activities.length > 10) {
        activities.pop();
    }

    renderActivities();
}

// Render activities
function renderActivities() {
    activityList.innerHTML = '';

    if (activities.length === 0) {
        activityList.innerHTML = '<div class="activity-item">No recent activity</div>';
        return;
    }

    activities.forEach(activity => {
        const div = document.createElement('div');
        div.className = 'activity-item';

        const timeAgo = getTimeAgo(activity.timestamp);

        div.innerHTML = `
            <div>${activity.action}: <strong>${activity.target}</strong></div>
            <div class="activity-time">${timeAgo}</div>
        `;

        activityList.appendChild(div);
    });
}

// Helper function to format time ago
function getTimeAgo(date) {
    const seconds = Math.floor((new Date() - date) / 1000);

    let interval = Math.floor(seconds / 31536000);
    if (interval >= 1) {
        return interval === 1 ? '1 year ago' : `${interval} years ago`;
    }

    interval = Math.floor(seconds / 2592000);
    if (interval >= 1) {
        return interval === 1 ? '1 month ago' : `${interval} months ago`;
    }

    interval = Math.floor(seconds / 86400);
    if (interval >= 1) {
        return interval === 1 ? '1 day ago' : `${interval} days ago`;
    }

    interval = Math.floor(seconds / 3600);
    if (interval >= 1) {
        return interval === 1 ? '1 hour ago' : `${interval} hours ago`;
    }

    interval = Math.floor(seconds / 60);
    if (interval >= 1) {
        return interval === 1 ? '1 minute ago' : `${interval} minutes ago`;
    }

    return seconds < 10 ? 'just now' : `${seconds} seconds ago`;
}
