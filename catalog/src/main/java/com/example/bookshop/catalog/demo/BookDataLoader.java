package com.example.bookshop.catalog.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.bookshop.catalog.domain.Book;
import com.example.bookshop.catalog.domain.BookRepository;

@Component
@Profile("testdata")
public class BookDataLoader {
    private final BookRepository bookRepository;
    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        var book1 = new Book("1234567890", "Machine Learning Bootstrap", "John Doe", 18.99);
        var book2 = new Book("2345678901", "Java for all", "Alice Baker", 12.99);
        var book3 = new Book("3456789012", "Spring in Action", "Eve Carpenter", 24.99);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }
}
