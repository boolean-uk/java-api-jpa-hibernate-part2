package com.booleanuk.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Genre is required")
    private String genre;
    @JsonProperty("author_id")
    @NotNull(message = "Author is required")
    private Long authorId;
    @JsonProperty("publisher_id")
    @NotNull(message = "Publisher is required")
    private Long publisherId;
}