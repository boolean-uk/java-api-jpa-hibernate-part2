
package com.booleanuk.api.DTO;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;



public class BookDTO {

    private String title;
    private String genre;
    private Integer authorId;
    private Integer publisherId;

    public BookDTO(){

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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int author_ID) {
        this.authorId = author_ID;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisher_ID(int publisher_ID) {
        this.publisherId = publisher_ID;
    }

}
