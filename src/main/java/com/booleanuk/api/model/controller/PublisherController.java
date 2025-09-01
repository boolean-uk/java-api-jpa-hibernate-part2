package com.booleanuk.api.model.controller;

import com.booleanuk.api.model.dto.AuthorRequestDto;
import com.booleanuk.api.model.dto.BookListDto;
import com.booleanuk.api.model.dto.PublisherRequestDto;
import com.booleanuk.api.model.jpa.Author;
import com.booleanuk.api.model.jpa.Publisher;
import com.booleanuk.api.model.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<BookListDto.PublisherDto>> getAll() {
        return ResponseEntity.ok(publisherRepository.findAll().stream()
                .map(BookListDto.PublisherDto::new).toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            BookListDto.PublisherDto publisherDto = new BookListDto.PublisherDto(publisherRepository.findById(id).orElseThrow());
            return ResponseEntity.ok(publisherDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No publisher with that id were found.");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PublisherRequestDto publisherDto) {
        try {
            Publisher publisher = new Publisher(publisherDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(publisherRepository.save(publisher));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create publisher, please check all required fields are correct.");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody PublisherRequestDto publisherRequestDto) {
        try {
            Publisher publisher = publisherRepository.findById(id).orElseThrow();
            publisher.setName(publisherRequestDto.getName());
            publisher.setLocation(publisherRequestDto.getLocation());

            publisherRepository.save(publisher);
            BookListDto.PublisherDto publisherResponse = new BookListDto.PublisherDto(publisherRepository.save(publisher));
            return ResponseEntity.ok(publisherResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not update publisher, please check all required fields are correct.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            Publisher publisher = publisherRepository.findById(id).orElseThrow();
            publisherRepository.deleteById(id);
            BookListDto.PublisherDto publisherResponse = new BookListDto.PublisherDto(publisher);
            return ResponseEntity.ok(publisherResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not delete publisher, please check the id is correct.");
        }
    }
}
