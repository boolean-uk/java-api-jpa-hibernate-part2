package com.booleanuk.api.dtos;


import lombok.Data;

import java.util.List;

@Data
public class AuthorWithBooksDTO {
//    private Long authorId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean alive;
    private List<BookWithPublisherDTO> books;
}