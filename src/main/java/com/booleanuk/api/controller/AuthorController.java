package com.booleanuk.api.controller;


import com.booleanuk.api.model.dto.AuthorDto;
import com.booleanuk.api.model.pojo.Author;
import com.booleanuk.api.model.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> result = new ArrayList<>();

        for (int i = 0; i < authors.size(); i++) {
            Author a = authors.get(i);
            result.add(toDto(a));
        }
        return result;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto getAuthorById(@PathVariable int id) {
        Author author = authorRepository.findById(id).orElse(null);
        return toDto(author);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorDto dto) {
        Author toSave =  new Author(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.isAlive()
        );

        Author saved = authorRepository.save(toSave);
        return toDto(saved);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@RequestBody AuthorDto dto, @PathVariable int id) {
        Author toUpdate = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        toUpdate.setFirstName(dto.firstName());
        toUpdate.setLastName(dto.lastName());
        toUpdate.setEmail(dto.email());
        toUpdate.setAlive(dto.isAlive());
        Author saved = authorRepository.save(toUpdate);
        return toDto(saved);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto delete(@PathVariable int id) {
        Author toDelete = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AuthorDto dto = toDto(toDelete);
        authorRepository.delete(toDelete);

        return dto;
    }

    private AuthorDto toDto(Author author){
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getEmail(),
                author.isAlive()
        );
    }



}
