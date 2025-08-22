package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.Book;
import com.booleanuk.api.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
