package com.booleanuk.api.dtos;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String title;

    private String genre;

    private int authorId;

    private int publisherId;

    public Book toBook(Author author, Publisher publisher){
        return new Book(title, genre, author, publisher);
    }

}
