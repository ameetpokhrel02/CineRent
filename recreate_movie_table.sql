-- SQL script to recreate the movie table with the correct structure

-- Drop the table if it exists
DROP TABLE IF EXISTS movie;

-- Create the movie table with all required fields
CREATE TABLE movie (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Genre VARCHAR(255) NOT NULL,
    ReleaseYear INT NOT NULL,
    Rating FLOAT NOT NULL,
    Price VARCHAR(50) NOT NULL,
    Runtime VARCHAR(50) NOT NULL,
    youtubeLink VARCHAR(255),
    overview TEXT,
    posterPath VARCHAR(255)
);

-- Insert sample data
INSERT INTO movie (Title, Genre, ReleaseYear, Rating, Price, Runtime, youtubeLink, overview, posterPath) VALUES
('Avengers: Endgame', 'Action, Adventure, Sci-Fi', 2019, 8.4, '12.99', '181 min', 'https://www.youtube.com/embed/TcMBFSGVi1c', 'After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.', 'https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg'),

('The Dark Knight', 'Action, Crime, Drama', 2008, 9.0, '9.99', '152 min', 'https://www.youtube.com/embed/EXeTwQWrcwY', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg'),

('Inception', 'Action, Adventure, Sci-Fi', 2010, 8.8, '10.99', '148 min', 'https://www.youtube.com/embed/YoHD9XEInc0', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg'),

('Pulp Fiction', 'Crime, Drama', 1994, 8.9, '8.99', '154 min', 'https://www.youtube.com/embed/s7EdQ4FqbhY', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg'),

('The Matrix', 'Action, Sci-Fi', 1999, 8.7, '7.99', '136 min', 'https://www.youtube.com/embed/vKQi3bBA1y8', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg');

-- Verify the data was inserted
SELECT * FROM movie;
