package com.booleanuk.api;

import com.booleanuk.api.dtos.CreateBookDTO;
import com.booleanuk.api.model.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(CreateBookDTO.class, Book.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getAuthorId(), (dest, v) -> dest.getAuthor().setAuthorId((Long) v));
                    mapper.map(src -> src.getPublisherId(), (dest, v) -> dest.getPublisher().setPublisherId((Long) v));
                });

        return modelMapper;
    }
}