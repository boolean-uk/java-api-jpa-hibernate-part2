package com.booleanuk.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private String genre;
    @JsonProperty("author_id")
    private int authorId;
    @JsonProperty("publisher_id")
    private int publisherId;
}
