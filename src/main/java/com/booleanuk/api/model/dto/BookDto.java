package com.booleanuk.api.model.dto;

public record BookDto(
        int id,
        String title,
        String genre,
        int author_id,
        int publisher_id
) {}
