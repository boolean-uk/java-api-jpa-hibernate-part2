package com.booleanuk.api.publishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publishers;

    @GetMapping
    public List<Publisher> getAll(){

    }

    @GetMapping
    public ResponseEntity<Publisher> getOne(){

    }

    @PostMapping
    public ResponseEntity<Publisher> create(){

    }

    @PutMapping
    public ResponseEntity<Publisher> update(){

    }

    @DeleteMapping
    public ResponseEntity<Publisher> delete(){

    }
}
