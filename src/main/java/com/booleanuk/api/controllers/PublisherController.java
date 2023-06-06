package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepo;
import com.booleanuk.api.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = publisherRepo.findAll();

        if(publishers.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers were found");

        return publishers;
    }

}
