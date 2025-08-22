package com.booleanuk.api.publishers;

import com.booleanuk.api.authors.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
}