package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // Custom query, can return other Projection objects if we dont want to return the entire object
    @Query("SELECT b FROM Book b WHERE b.author.id = ?1")
    List<Book> findAllBooks(int id);
}
