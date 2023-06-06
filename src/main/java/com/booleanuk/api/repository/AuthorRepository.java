package com.booleanuk.api.repository;

import com.booleanuk.api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByFirstNameAndLastNameAndEmailAndAlive(String firstName, String lastName, String email, boolean alive);
}
