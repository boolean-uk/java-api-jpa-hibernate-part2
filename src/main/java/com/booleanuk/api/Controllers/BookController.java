package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Author;
import com.booleanuk.api.Models.Book;
import com.booleanuk.api.Models.Publisher;
import com.booleanuk.api.Repositories.AuthorRepository;
import com.booleanuk.api.Repositories.BookRepository;
import com.booleanuk.api.Repositories.PublisherRepository;
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
    BookRepository repo;

    @Autowired
    AuthorRepository authRepo;

    @Autowired
    PublisherRepository pubRepo;

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id){
        Book found = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with provided ID not found")
        );
        return ResponseEntity.ok(found);
    }

    public record addBook(String title, String genre, int author_id, int publisher_id){}

     @PostMapping
     public ResponseEntity<Book> add(@RequestBody addBook book){
         Author auth = this.authRepo.findById(book.author_id).orElseThrow(() ->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with provided ID found")
         );

         Publisher pub = this.pubRepo.findById(book.publisher_id).orElseThrow(() ->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with provided ID found")
         );

         Book newBook = new Book(book.title, book.genre, auth, pub);
         return new ResponseEntity<>(this.repo.save(newBook), HttpStatus.CREATED);
     }

     @PutMapping("{id}")
     public ResponseEntity<Book> putOne(@PathVariable int id, @RequestBody addBook book){
         Author auth = this.authRepo.findById(book.author_id).orElseThrow(() ->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with provided ID found")
         );

         Publisher pub = this.pubRepo.findById(book.publisher_id).orElseThrow(() ->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with provided ID found")
         );

         Book oldBook = this.repo.findById(id).orElseThrow(() ->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with provided ID found")
         );

         oldBook.setTitle(book.title);
         oldBook.setGenre(book.genre);
         oldBook.setAuthor(auth);
         oldBook.setPublisher(pub);

         return new ResponseEntity<>(this.repo.save(oldBook), HttpStatus.CREATED);
     }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteOne(@PathVariable int id){
        Book toBeDeleted = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with provided ID not found")
        );
        this.repo.delete(toBeDeleted);
        return ResponseEntity.ok(toBeDeleted);
    }
}
