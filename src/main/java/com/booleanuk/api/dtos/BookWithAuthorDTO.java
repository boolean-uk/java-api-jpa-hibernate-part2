package com.booleanuk.api.dtos;

import lombok.Data;

@Data
public class BookWithAuthorDTO {
    private String title;
    private String genre;
    private AuthorDTO author;
}