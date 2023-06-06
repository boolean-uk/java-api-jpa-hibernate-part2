package com.booleanuk.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookViewDTO {

    private String title;

    private String genre;

    private AuthorDTO author;

    private PublisherDTO publisher;

   /* public Book toBook(Author author, Publisher publisher){
        return new Book(title, genre, author, publisher);
    }*/

}
