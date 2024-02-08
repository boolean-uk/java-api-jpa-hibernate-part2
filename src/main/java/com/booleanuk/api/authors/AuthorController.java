package com.booleanuk.api.authors;

import com.booleanuk.api.authors.dto.AuthorDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id) {
        Author author = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AuthorDTO> addOne(@Valid @RequestBody Author author) {
        return new ResponseEntity<>(
                modelMapper.map(this.authorRepository.save(author), AuthorDTO.class),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateOne(@PathVariable int id, @Valid @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());

        return new ResponseEntity<>(
                modelMapper.map(this.authorRepository.save(authorToUpdate), AuthorDTO.class),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> deleteOne(@PathVariable int id){
        Author author = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));

        try{
            this.authorRepository.delete(author);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author have books");
        }
        return new ResponseEntity<>(modelMapper.map(author, AuthorDTO.class), HttpStatus.OK);
    }
}
