package com.booleanuk.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Genre is required")
    private String genre;
    @NotNull(message = "Author is required")
    private Long authorId;
    @NotNull(message = "Publisher is required")
    private Long publisherId;
}