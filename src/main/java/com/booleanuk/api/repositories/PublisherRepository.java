package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    @Query("SELECT p FROM Publisher p WHERE p.location ILIKE ?1")
    List<Publisher> findAllByLocation(String location);
}
