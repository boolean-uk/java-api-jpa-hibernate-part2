package com.booleanuk.api.models.dtos;

//record BookRequestBodyDTO (String title, String genre, int author_id, int publisher_id) {}
public class BookRequestBodyDTO {
    private String title;
    private String genre;
    private int author_id;
    private int publisher_id;

    public BookRequestBodyDTO() {}

    public BookRequestBodyDTO(String title, String genre, int author_id, int publisher_id) {
        this.title = title;
        this.genre = genre;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }
}
