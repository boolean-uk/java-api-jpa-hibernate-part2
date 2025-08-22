CREATE DATABASE java_api_jpa_hibernate_part2;

\c java_api_jpa_hibernate_part2;

CREATE TABLE authors(
  id SERIAL PRIMARY KEY,
  first_name TEXT,
  last_name TEXT,
  email TEXT,
  alive BOOLEAN
);

CREATE TABLE publishers(
  id SERIAL PRIMARY KEY,
  name TEXT,
  location TEXT
);

CREATE TABLE books(
  id SERIAL PRIMARY KEY,
  title TEXT,
  genre TEXT,

  author_id INTEGER REFERENCES authors(id),
  publisher_id INTEGER REFERENCES publishers(id)
);
