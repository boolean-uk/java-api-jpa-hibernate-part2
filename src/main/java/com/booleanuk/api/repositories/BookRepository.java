package com.booleanuk.api.repositories;

import com.booleanuk.api.modules.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
