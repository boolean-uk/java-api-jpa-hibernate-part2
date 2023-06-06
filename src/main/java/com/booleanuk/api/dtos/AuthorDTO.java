package com.booleanuk.api.dtos;


import com.booleanuk.api.models.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {


    private String firstName;

    private String lastName;

    private String email;

    private boolean alive;


    public static AuthorDTO fromAuthor(Author author){
        return new AuthorDTO(author.getFirstName(), author.getLastName(), author.getEmail(), author.isAlive());
    }
}
