package com.booleanuk.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PublisherWithBooksDTO {
    private String name;
    private String location;
    private List<BookWithAuthorDTO> books;
}