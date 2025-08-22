ALTER TABLE books
ADD CONSTRAINT fk_author_id
FOREIGN KEY (author_id)
REFERENCES authors(author_id);

