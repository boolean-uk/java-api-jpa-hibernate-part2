package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    //List<Author> findAllByLocation(String location);
}
