package com.booleanuk.api.services;

import com.booleanuk.api.dtos.BookDTO;
import com.booleanuk.api.dtos.BookViewDTO;
import com.booleanuk.api.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookService {

    BookViewDTO generateDTO(Book book);
    List<BookViewDTO> generateDTOsList(List<Book> books);

    Book createBook(BookDTO bookDTO);

    Book updateBook(int id, BookDTO bookDTO);
}
