package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepsitory extends JpaRepository<Author, Integer> {
}
