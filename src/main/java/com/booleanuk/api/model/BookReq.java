package com.booleanuk.api.model;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookReq {
    private String title;
    private String genre;
    private int author_id;
    private int publisher_id;
}

// need this class in order for spec to work
