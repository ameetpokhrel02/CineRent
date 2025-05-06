-- SQL script to add overview and posterPath columns to the movie table

-- Check if the columns already exist, if not add them
ALTER TABLE movie ADD COLUMN IF NOT EXISTS overview TEXT;
ALTER TABLE movie ADD COLUMN IF NOT EXISTS posterPath VARCHAR(255);

-- If your database doesn't support IF NOT EXISTS, you can use this alternative approach:
-- First check if the column exists
-- SELECT COUNT(*) INTO @exists FROM information_schema.columns WHERE table_schema = 'cinerent' AND table_name = 'movie' AND column_name = 'overview';
-- IF @exists = 0 THEN
--     ALTER TABLE movie ADD COLUMN overview TEXT;
-- END IF;

-- SELECT COUNT(*) INTO @exists FROM information_schema.columns WHERE table_schema = 'cinerent' AND table_name = 'movie' AND column_name = 'posterPath';
-- IF @exists = 0 THEN
--     ALTER TABLE movie ADD COLUMN posterPath VARCHAR(255);
-- END IF;

-- Sample update statements to add data to existing movies
-- UPDATE movie SET overview = 'After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.' WHERE Title = 'Avengers: Endgame';
-- UPDATE movie SET posterPath = 'https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg' WHERE Title = 'Avengers: Endgame';

-- UPDATE movie SET overview = 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.' WHERE Title = 'The Dark Knight';
-- UPDATE movie SET posterPath = 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg' WHERE Title = 'The Dark Knight';

-- UPDATE movie SET overview = 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.' WHERE Title = 'Inception';
-- UPDATE movie SET posterPath = 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg' WHERE Title = 'Inception';
