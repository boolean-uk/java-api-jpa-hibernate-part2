package com.booleanuk.api.dtos;

import com.booleanuk.api.models.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private String name;
    private String location;

    public static PublisherDTO fromPublisher(Publisher publisher){
        return new PublisherDTO(publisher.getName(), publisher.getLocation());
    }
}
