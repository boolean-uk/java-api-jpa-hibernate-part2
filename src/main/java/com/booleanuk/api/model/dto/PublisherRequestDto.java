package com.booleanuk.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublisherRequestDto {
    private String name;
    private String location;
}
