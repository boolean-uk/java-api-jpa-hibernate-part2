package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ModelDtos {

    // === Book DTO ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookDTO {
        private String title;
        private String genre;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private int author_id;
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private int publisher_id;

        private AuthorDTO authorDTO;
        private PublisherDTO publisherDTO;

        public BookDTO(String title, String genre) {
            this.title = title;
            this.genre = genre;
        }

        public BookDTO(String title, String genre, AuthorDTO authorDTO, PublisherDTO publisherDTO) {
            this.title = title;
            this.genre = genre;
            this.authorDTO = authorDTO;
            this.publisherDTO = publisherDTO;
        }
    }

    // === Author DTO ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthorDTO {
        private String firstName;
        private String lastName;
        private String email;
        private boolean alive;
        private List<BookDTO> books;

        public AuthorDTO(String firstName, String lastName, String email, boolean alive) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.alive = alive;
        }
    }

    // === Publisher DTO ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PublisherDTO {
        private String name;
        private String location;
        private List<BookDTO> books;

        public PublisherDTO(String name, String location) {
            this.name = name;
            this.location = location;
        }
    }
}
