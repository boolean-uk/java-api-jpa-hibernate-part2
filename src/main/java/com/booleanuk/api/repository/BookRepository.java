package com.booleanuk.api.repository;

import com.booleanuk.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
