package com.booleanuk.api.model.repository;

import com.booleanuk.api.model.jpa.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
