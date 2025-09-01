package com.booleanuk.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AuthorDto(
        int id,
        String firstName,
        String lastName,
        String email,
        boolean isAlive
) {}