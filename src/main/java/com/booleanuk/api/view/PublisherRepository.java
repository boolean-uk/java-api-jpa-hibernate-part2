package com.booleanuk.api.view;

import com.booleanuk.api.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
}

