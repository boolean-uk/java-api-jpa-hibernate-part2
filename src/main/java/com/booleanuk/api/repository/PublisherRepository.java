package com.booleanuk.api.repository;

import com.booleanuk.api.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}