package com.booleanuk.api.model;

public class BookDTO {
    private Integer id;
    private String title;
    private String genre;
    private Integer authorId;
    private Integer publisherId;

    // Default constructor
    public BookDTO() {
    }

    // Constructor with fields
    public BookDTO(Integer id, String title, String genre, Integer authorId, Integer publisherId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.authorId = authorId;
        this.publisherId = publisherId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }
}
