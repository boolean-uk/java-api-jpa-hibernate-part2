package com.booleanuk.api.publisher;

import com.booleanuk.api.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
