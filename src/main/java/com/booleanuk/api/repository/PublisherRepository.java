package com.booleanuk.api.repository;

import com.booleanuk.api.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Optional<Publisher> findByNameAndLocation(String name, String location);
}
