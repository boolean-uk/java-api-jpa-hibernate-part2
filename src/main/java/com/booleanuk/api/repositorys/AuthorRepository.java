package com.booleanuk.api.repositorys;

import com.booleanuk.api.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
