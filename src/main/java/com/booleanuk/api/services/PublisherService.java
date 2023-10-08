package com.booleanuk.api.services;

import com.booleanuk.api.dtos.PublisherDTO;
import com.booleanuk.api.dtos.PublisherWithBooksDTO;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, ModelMapper modelMapper) {
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
    }

    public List<PublisherDTO> getAllPublisherDTOs() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .map(publisher -> modelMapper.map(publisher, PublisherDTO.class))
                .collect(Collectors.toList());
    }

    public PublisherWithBooksDTO getPublisherWithBooksDTOById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found."));

        return modelMapper.map(publisher, PublisherWithBooksDTO.class);
    }

    public Publisher findById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found with id: " + id));
    }

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = modelMapper.map(publisherDTO, Publisher.class);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return modelMapper.map(savedPublisher, PublisherDTO.class);
    }

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher existingPublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found."));

        existingPublisher.setName(publisherDTO.getName());
        existingPublisher.setLocation(publisherDTO.getLocation());

        Publisher updatedPublisher = publisherRepository.save(existingPublisher);
        return modelMapper.map(updatedPublisher, PublisherDTO.class);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
