-- INITIAL DATA (ENABLE/DISABLE IN APPLICATION.YML)
-- Insert authors
INSERT INTO author (alive, email, first_name, last_name) VALUES
    (false, 'isaac@monlith.com', 'Isaac', 'Asimov'),
    (false, 'mail@tolkien.org', 'J.R.R.', 'Tolkien'),
    (true, 'willgib77@gmail.com', 'William', 'Gibson'),
    (true, 'madelmill@hotmail.com', 'Madeline', 'Miller'),
    (true, 'hatrack@gmail.com', 'Orson', 'Scott Card'),
    (true, 'jk.rowling1829@gmail.net', 'J.K.', 'Rowling');

-- Insert publishers
INSERT INTO publisher (location, name) VALUES
    ('New York', 'Doubleday'),
    ('London', 'HarperCollins'),
    ('London', 'Bloomsbury'),
    ('New York', 'Little, Brown and Company'),
    ('Edinburgh', 'Canongate Books');

-- Insert books
INSERT INTO book (author_id, publisher_id, genre, title) VALUES
    (1, 1, 'Sci-Fi', 'Foundation'),
    (2, 3, 'Fantasy', 'The Lord of the Rings'),
    (2, 3, 'Fantasy', 'The Hobbit'),
    (3, 1, 'Sci-Fi', 'Neuromancer'),
    (4, 4, 'Fantasy', 'Circe'),
    (4, 4, 'Fantasy', 'The Midnight Library'),
    (5, 1, 'Sci-Fi', 'Ender''s Game'),
    (6, 3, 'Fantasy', 'Harry Potter');
