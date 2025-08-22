package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    //List<Publisher> findAllByLocation(String location);
}
