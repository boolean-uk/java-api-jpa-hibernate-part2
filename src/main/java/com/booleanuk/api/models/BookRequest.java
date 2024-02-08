package com.booleanuk.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String title;
    private String genre;
    private int authorId;
    private int publisherId;
}
