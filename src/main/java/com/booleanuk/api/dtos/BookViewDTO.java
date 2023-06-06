package com.booleanuk.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookViewDTO {

    private int id;

    private String title;

    private String genre;

    private AuthorDTO author;

    private PublisherDTO publisher;

}
