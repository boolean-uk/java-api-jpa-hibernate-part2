package com.booleanuk.api.model.repository;

import com.booleanuk.api.model.pojo.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
