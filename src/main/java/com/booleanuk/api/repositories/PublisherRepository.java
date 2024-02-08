package com.booleanuk.api.repositories;

import com.booleanuk.api.modules.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
