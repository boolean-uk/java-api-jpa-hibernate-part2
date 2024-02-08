package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
}
