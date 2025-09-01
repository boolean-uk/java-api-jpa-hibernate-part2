package com.booleanuk.api.model.repository;

import com.booleanuk.api.model.pojo.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
