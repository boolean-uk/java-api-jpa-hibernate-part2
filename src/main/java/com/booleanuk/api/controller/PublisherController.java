package com.booleanuk.api.controller;


import com.booleanuk.api.dtos.PublisherDTO;
import com.booleanuk.api.dtos.PublisherWithBooksDTO;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import com.booleanuk.api.services.PublisherService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;
    private final PublisherService publisherService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDTO> getAllPublishers() {
        return publisherService.getAllPublisherDTOs();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PublisherWithBooksDTO> getPublisherById(@PathVariable Long id) {
        PublisherWithBooksDTO publisherDTO = publisherService.getPublisherWithBooksDTOById(id);
        return ResponseEntity.ok(publisherDTO);
    }

    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody @Valid PublisherDTO publisherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        PublisherDTO createdPublisherDTO = publisherService.createPublisher(publisherDTO);
        return new ResponseEntity<>(createdPublisherDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        PublisherDTO updatedPublisherDTO = publisherService.updatePublisher(id, publisherDTO);
        return ResponseEntity.ok(updatedPublisherDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PublisherDTO> deletePublisher(@PathVariable Long id) {
        Publisher toDelete = publisherService.findById(id);
        if (toDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found.");
        }
        publisherService.deletePublisher(id);

        PublisherDTO deletedPublisherDTO = mapToPublisherDTO(toDelete);
        return ResponseEntity.ok(deletedPublisherDTO);
    }

    private PublisherDTO mapToPublisherDTO(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setPublisherId(publisher.getPublisherId());
        publisherDTO.setName(publisher.getName());
        publisherDTO.setLocation(publisher.getLocation());
        return publisherDTO;
    }
}
