package com.booleanuk.api.authors.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean alive;

}
