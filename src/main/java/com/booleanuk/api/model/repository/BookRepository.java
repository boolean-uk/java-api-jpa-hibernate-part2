package com.booleanuk.api.model.repository;

import com.booleanuk.api.model.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {}
