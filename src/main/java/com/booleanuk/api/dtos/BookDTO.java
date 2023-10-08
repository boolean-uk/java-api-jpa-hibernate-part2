package com.booleanuk.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {
    private Long bookId;
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @Valid
    private AuthorDTO author;

    @Valid
    private PublisherDTO publisher;
}