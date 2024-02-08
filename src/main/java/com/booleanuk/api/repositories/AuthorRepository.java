package com.booleanuk.api.repositories;

import com.booleanuk.api.modules.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
