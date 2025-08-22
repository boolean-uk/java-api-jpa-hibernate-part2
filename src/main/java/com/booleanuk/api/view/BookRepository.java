package com.booleanuk.api.view;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
