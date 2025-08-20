package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
