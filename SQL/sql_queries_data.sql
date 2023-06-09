INSERT INTO publishers (name , location)
VALUES ('Bloomsbury','UK'),
        ('Allen & Unwin','UK'),
        ('Harper & Brothers', 'US');

INSERT INTO authors (first_name, last_name,email,alive)
VALUES ('Joanne', 'Rowling', 'jkrowling@email.com', true),
        ('John Ronald Reuel', 'Tolkien', 'jrrtolkien@email.com', false),
        ('Elwyn Brooks', 'White', 'ebwhite@email.com', false);

INSERT INTO books (title,genre,publisher_id, author_id)
VALUES ('Charlotte''s Web', 'Children''s', 3,3),
        ('Harry Potter and the Prisoner of Azkaban', 'Fantasy',1,1),
        ('Harry Potter and the Philosopher''s Stone', 'Fantasy',1,1),
        ('The Fellowship of the Ring','Fantasy',2,2);