package com.booleanuk.api.publishers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    boolean existsByNameAndLocation(String name, String location);
}
