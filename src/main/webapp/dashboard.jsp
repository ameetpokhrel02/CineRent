<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="brand">
                <h2>Admin Panel</h2>
            </div>
            <ul class="nav">
                <li class="nav-item active" data-target="dashboard">
                    <span class="material-icons">dashboard</span>
                    <span>Dashboard</span>
                </li>
                <li class="nav-item" data-target="users">
                    <span class="material-icons">people</span>
                    <span>Users</span>
                </li>
                <li class="nav-item" data-target="movies">
                    <span class="material-icons">movie</span>
                    <span>Movies</span>
                </li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <div class="header">
                <div class="search-container">
                    <span class="material-icons">search</span>
                    <input type="text" placeholder="Search..." id="searchInput">
                </div>
                <div class="user-info">
                    <span class="material-icons">notifications</span>
                    <span class="material-icons">account_circle</span>

                    <a href="home.jsp">Home</a>

                </div>
            </div>

            <!-- Dashboard Section -->
            <div class="content-section active" id="dashboard">
                <h1>Dashboard</h1>
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-value" id="userCount">0</div>
                        <div class="stat-label">Users</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value" id="movieCount">0</div>
                        <div class="stat-label">Movies</div>
                    </div>
                </div>
                <div class="recent-activity">
                    <h2>Recent Activity</h2>
                    <div class="activity-list" id="activityList">
                        <!-- Activity items will be added here -->
                    </div>
                </div>
            </div>

            <!-- Users Section -->
            <div class="content-section" id="users">
                <div class="section-header">
                    <h1>Users</h1>
                    <button class="add-btn" id="addUserBtn">Add User</button>
                </div>
                <div class="table-container">
                    <table id="usersTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Phone</th>
                                <th>Role</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- User data will be added here -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Movies Section -->
            <div class="content-section" id="movies">
                <div class="section-header">
                    <h1>Movies</h1>
                    <button class="add-btn" id="addMovieBtn">Add Movie</button>
                </div>
                <div class="table-container">
                    <table id="moviesTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Genre</th>
                                <th>Release Year</th>
                                <th>Rating</th>
                                <th>Price</th>
                                <th>Runtime</th>
                                <th>Overview</th>
                                <th>Poster</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Movie data will be added here -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- User Modal -->
    <div class="modal" id="userModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="userModalTitle">Add User</h2>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
            <form id="userForm" method="post" action="UserServlet">
    <input type="hidden" id="userId" name="userId">

    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
    </div>

    <div class="form-group">
        <label for="firstName">First Name</label>
        <input type="text" id="firstName" name="firstName" required>
    </div>

    <div class="form-group">
        <label for="lastName">Last Name</label>
        <input type="text" id="lastName" name="lastName" required>
    </div>

    <div class="form-group">
        <label for="phone">Phone Number</label>
        <input type="text" id="phone" name="phone" required>
    </div>

    <div class="form-group">
        <label for="profilePic">Profile Picture URL</label>
        <input type="text" id="profilePic" name="profilePic">
    </div>

    <div class="form-group">
        <label for="role">Role</label>
        <select id="role" name="role" required>
            <option value="user">User</option>
            <option value="admin">Admin</option>
        </select>
    </div>

    <div class="form-actions">
        <button type="submit" class="save-btn">Save</button>
        <button type="button" class="cancel-btn">Cancel</button>
    </div>
</form>

            </div>
        </div>
    </div>

    <div class="modal" id="movieModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="movieModalTitle">Add Movie</h2>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <form id="movieForm" action="MovieServlet" method="post" >
                    <input type="hidden" id="movieId" name="movieId">

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" name="title" id="title" required>
                    </div>

                    <div class="form-group">
                        <label for="genre">Genre</label>
                        <input type="text" name="genre" id="genre" required>
                    </div>

                    <div class="form-group">
                        <label for="releaseYear">Release Year</label>
                        <input type="number"  name="releaseYear" id="releaseYear" required>
                    </div>

                    <div class="form-group">
                        <label for="rating">Rating</label>
                        <input type="number" id="rating" name="rating" min="0" max="10" step="0.1" required>
                    </div>

                    <div class="form-group">
                        <label for="price">Price</label>
                        <input type="number" id="price" name="price" min="0" step="0.01" required>
                    </div>

                    <div class="form-group">
                        <label for="runtime">Runtime (minutes)</label>
                        <input type="number" id="runtime" name="runtime" min="0" required>
                    </div>

                    <div class="form-group">
                        <label for="youtubeLink">YouTube Link</label>
                        <input type="text" id="youtubeLink" name="youtubeLink" >
                    </div>

                    <div class="form-group">
                        <label for="overview">Overview</label>
                        <textarea id="overview" name="overview" rows="4"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="posterPath">Poster Image URL</label>
                        <input type="text" id="posterPath" name="posterPath">
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="save-btn">Save</button>
                        <button type="button" class="cancel-btn">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal" id="deleteModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Confirm Delete</h2>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <p id="deleteMessage">Are you sure you want to delete this item?</p>
                <div class="form-actions">
                    <button id="confirmDelete" class="delete-btn">Delete</button>
                    <button class="cancel-btn">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <script src="js/dashboard.js"></script>
</body>
</html>