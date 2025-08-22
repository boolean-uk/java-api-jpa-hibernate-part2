ALTER TABLE books
ADD CONSTRAINT fk_publisher_id
FOREIGN KEY (publisher_id)
REFERENCES publishers(publisher_id);