package com.booleanuk.api.controller;

import com.booleanuk.api.model.dto.PublisherDto;
import com.booleanuk.api.model.pojo.Publisher;
import com.booleanuk.api.model.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PublisherDto> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        List<PublisherDto> result = new ArrayList<>();

        for (Publisher p : publishers) {
            result.add(toDto(p));
        }
        return result;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherDto getPublisherById(@PathVariable int id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(publisher);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDto createPublisher(@RequestBody PublisherDto dto) {
        Publisher toSave = new Publisher(dto.name(), dto.location());
        Publisher saved = publisherRepository.save(toSave);
        return toDto(saved);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherDto updatePublisher(@RequestBody PublisherDto dto, @PathVariable int id) {
        Publisher toUpdate = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        toUpdate.setName(dto.name());
        toUpdate.setLocation(dto.location());

        Publisher saved = publisherRepository.save(toUpdate);
        return toDto(saved);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherDto deletePublisher(@PathVariable int id) {
        Publisher toDelete = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PublisherDto dto = toDto(toDelete);
        publisherRepository.delete(toDelete);
        return dto;
    }

    private PublisherDto toDto(Publisher publisher) {
        return new PublisherDto(
                publisher.getId(),
                publisher.getName(),
                publisher.getLocation()
        );
    }
}
