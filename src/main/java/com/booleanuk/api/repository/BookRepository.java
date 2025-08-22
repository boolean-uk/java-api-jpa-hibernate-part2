package com.booleanuk.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booleanuk.api.model.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {
}
