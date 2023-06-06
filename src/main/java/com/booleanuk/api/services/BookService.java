package com.booleanuk.api.services;

import com.booleanuk.api.dtos.AuthorDTO;
import com.booleanuk.api.dtos.BookDTO;
import com.booleanuk.api.dtos.BookViewDTO;
import com.booleanuk.api.dtos.PublisherDTO;
import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepo;
import com.booleanuk.api.repositories.BookRepo;
import com.booleanuk.api.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements IBookService {


    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    PublisherRepo publisherRepo;

    @Override
    public BookViewDTO generateDTO(Book book) {
        return new BookViewDTO(book.getId(), book.getTitle(), book.getGenre(),
                AuthorDTO.fromAuthor(book.getAuthor()), PublisherDTO.fromPublisher(book.getPublisher()));
    }

    @Override
    public List<BookViewDTO> generateDTOsList(List<Book> books) {
        List<BookViewDTO> bookDTOS = new ArrayList<>();

        for (Book book : books) {
            BookViewDTO bookListDTO = new BookViewDTO(book.getId(), book.getTitle(), book.getGenre(),
                    AuthorDTO.fromAuthor(book.getAuthor()), PublisherDTO.fromPublisher(book.getPublisher()));

            bookDTOS.add(bookListDTO);
        }

        return bookDTOS;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Author author = authorRepo.findById(bookDTO.getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find an author with the given id"));
        Publisher publisher = publisherRepo.findById(bookDTO.getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find a publisher with the given id"));

        return bookRepo.save(bookDTO.toBook(author, publisher));

    }

    @Override
    public Book updateBook(int id, BookDTO bookDTO) {
        Book bookToUpdate = bookRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books matching that id were found"));

        Author author = authorRepo.findById(bookDTO.getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find an author with the given id"));
        Publisher publisher = publisherRepo.findById(bookDTO.getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find a publisher with the given id"));

        bookToUpdate.setTitle(bookDTO.getTitle());
        bookToUpdate.setGenre(bookDTO.getGenre());
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setAuthor(author);

        return bookRepo.save(bookToUpdate);
    }
}
