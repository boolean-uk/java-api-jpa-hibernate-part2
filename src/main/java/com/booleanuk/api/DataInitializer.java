package com.booleanuk.api;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class DataInitializer {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final Faker faker;

    @Autowired
    public DataInitializer(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.faker = new Faker();
    }

    public void initializeData() {
        CountDownLatch dataInitializationLatch = new CountDownLatch(15);

        for (int i = 0; i < 5; i++) {
            Publisher publisher = new Publisher();
            publisher.setName(faker.company().name());
            publisher.setLocation(faker.address().city());
            publisherRepository.save(publisher);
            dataInitializationLatch.countDown();

        }

        for (int i = 0; i < 10; i++) {
            Author author = new Author();
            author.setFirstName(faker.name().firstName());
            author.setLastName(faker.name().lastName());
            author.setEmail(faker.internet().emailAddress());
            author.setAlive(faker.random().nextBoolean());
            authorRepository.save(author);
            dataInitializationLatch.countDown();
        }

        try {
            dataInitializationLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Data initialization interrupted", e);
        }

        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setTitle(faker.book().title());
            book.setGenre(faker.book().genre());

            Author randomAuthor = authorRepository.findById(Long.valueOf(faker.random().nextInt(1, 11)))
                    .orElseThrow(() -> new RuntimeException("Random Author not found"));
            Publisher randomPublisher = publisherRepository.findById(Long.valueOf(faker.random().nextInt(1, 6)))
                    .orElseThrow(() -> new RuntimeException("Random Publisher not found"));

            // TODO: Make this asynchronous? So that you do not get there errors?
            book.setAuthor(randomAuthor);
            book.setPublisher(randomPublisher);

            bookRepository.save(book);
        }
    }
}