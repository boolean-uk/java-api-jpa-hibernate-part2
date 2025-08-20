package com.booleanuk.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.booleanuk.api.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
