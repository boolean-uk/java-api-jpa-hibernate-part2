package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
