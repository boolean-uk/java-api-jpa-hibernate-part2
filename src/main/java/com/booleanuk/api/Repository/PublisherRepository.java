package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.Author;
import com.booleanuk.api.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
