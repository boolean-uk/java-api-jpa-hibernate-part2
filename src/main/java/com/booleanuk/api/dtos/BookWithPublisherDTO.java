package com.booleanuk.api.dtos;

import lombok.Data;

@Data
public class BookWithPublisherDTO {
    private String title;
    private String genre;
    private PublisherDTO publisher;
}