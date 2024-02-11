-- Insert dummy publishers
INSERT INTO publishers (name, location) VALUES
('Publisher A', 'Location A'),
('Publisher B', 'Location B'),
('Publisher C', 'Location C');

-- Insert dummy authors
INSERT INTO authors (first_name, last_name, email, alive) VALUES
('John', 'Doe', 'john.doe@example.com', true),
('Jane', 'Smith', 'jane.smith@example.com', true),
('Michael', 'Johnson', 'michael.johnson@example.com', false),
('Emily', 'Brown', 'emily.brown@example.com', true),
('David', 'Wilson', 'david.wilson@example.com', true);

-- Insert dummy books
INSERT INTO books (title, genre, author_id, publisher_id) VALUES
('Book 1', 'Genre A', 1, 1),
('Book 2', 'Genre B', 2, 1),
('Book 3', 'Genre C', 3, 2),
('Book 4', 'Genre A', 4, 2),
('Book 5', 'Genre B', 5, 3),
('Book 6', 'Genre A', 1, 2);
