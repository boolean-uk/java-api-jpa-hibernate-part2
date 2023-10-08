package com.booleanuk.api.services;
import com.booleanuk.api.dtos.AuthorDTO;
import org.modelmapper.ModelMapper;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public Author findById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }
    public List<AuthorDTO> getAllAuthorDTOs() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
