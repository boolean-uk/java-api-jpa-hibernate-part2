package com.booleanuk.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublisherDTO {

    private Long publisherId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;
}