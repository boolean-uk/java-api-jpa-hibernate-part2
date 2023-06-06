package com.booleanuk.api.controllers;

import com.booleanuk.api.dtos.BookDTO;
import com.booleanuk.api.dtos.BookViewDTO;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.repositories.BookRepo;
import com.booleanuk.api.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    IBookService iBookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewDTO> getAllBooks() {
        List<Book> books = bookRepo.findAll();

        if(books.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books were found");


        return iBookService.generateDTOsList(books);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookViewDTO getBookById(@PathVariable int id){
        Book book;
        book = bookRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book by that id was found"));

        return iBookService.generateDTO(book);

    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO){

        try {
            return new ResponseEntity<>(iBookService.createBook(bookDTO), HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new book, please check all required fields are correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {

        try {
            return new ResponseEntity<>(iBookService.updateBook(id, bookDTO), HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the book's details, please check all required fields are correct.");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = bookRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books matching that id were found"));
        bookRepo.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

}
