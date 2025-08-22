package com.booleanuk.api.Controller;


import com.booleanuk.api.Model.Author;
import com.booleanuk.api.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Author newAuthor) {
        try {
            Author savedAuthor = this.authorRepository.save(newAuthor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create author, please check all required fields are correct.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        if(this.authorRepository.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(this.authorRepository.findById(id));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No authors with that id were found.");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Author updatedAuthor) {
        Optional<Author> existingAuthorOptional = this.authorRepository.findById(id);

        if (existingAuthorOptional.isPresent()) {
            try {
                Author existingAuthor = existingAuthorOptional.get();
                existingAuthor.setFirst_name(updatedAuthor.getFirst_name());
                existingAuthor.setLast_name(updatedAuthor.getLast_name());
                existingAuthor.setEmail(updatedAuthor.getEmail());
                existingAuthor.setAlive(updatedAuthor.isAlive());
                Author savedAuthor = this.authorRepository.save(existingAuthor);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Could not update author, please check all required fields are correct.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No authors with that id were found.");
        }
    }



    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Author delete(@PathVariable("id") Integer id) {
        Author returnAuthor = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No authors with that ID were found")
        );
        this.authorRepository.deleteById(id);
        return returnAuthor;
    }


}
