package com.booleanuk.api.authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authors;

    @GetMapping
    public List<Author> getAll(){

    }

    @GetMapping
    public ResponseEntity<Author> getOne(){

    }

    @PostMapping
    public ResponseEntity<Author> create(){

    }

    @PutMapping
    public ResponseEntity<Author> update(){

    }

    @DeleteMapping
    public ResponseEntity<Author> delete(){

    }
}
