package com.booleanuk.api.repository;

import com.booleanuk.api.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher, Integer> {
}
