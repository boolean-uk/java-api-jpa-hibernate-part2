package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
