package com.booleanuk.api.model.controller;

import com.booleanuk.api.model.dto.AuthorRequestDto;
import com.booleanuk.api.model.dto.BookListDto;
import com.booleanuk.api.model.jpa.Author;
import com.booleanuk.api.model.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<BookListDto.AuthorDto>> getAll() {
        return ResponseEntity.ok(authorRepository.findAll().stream()
                .map(BookListDto.AuthorDto::new).toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            BookListDto.AuthorDto authorDto = new BookListDto.AuthorDto(authorRepository.findById(id).orElseThrow());
            return ResponseEntity.ok(authorDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No author with that id were found.");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AuthorRequestDto authorDto) {
        try {
            Author author = new Author(authorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(author));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create author, please check all required fields are correct.");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody AuthorRequestDto authorDto) {
        try {
            Author author = authorRepository.findById(id).orElseThrow();
            author.setFirstName(authorDto.getFirstName());
            author.setLastName(authorDto.getLastName());
            author.setEmail(authorDto.getEmail());
            author.setAlive(authorDto.isAlive());

            authorRepository.save(author);
            BookListDto.AuthorDto authorResponse = new BookListDto.AuthorDto(authorRepository.save(author));
            return ResponseEntity.ok(authorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No author with that id were found.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            Author author = authorRepository.findById(id).orElseThrow();
            authorRepository.deleteById(id);
            BookListDto.AuthorDto authorResponse = new BookListDto.AuthorDto(author);
            return ResponseEntity.ok(authorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No author with that id were found.");
        }
    }


}
