package com.booleanuk.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.booleanuk.api.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
