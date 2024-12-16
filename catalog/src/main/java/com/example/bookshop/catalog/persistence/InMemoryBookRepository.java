package com.example.bookshop.catalog.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.bookshop.catalog.domain.Book;
import com.example.bookshop.catalog.domain.BookRepository;

@Repository
public class InMemoryBookRepository implements BookRepository{
    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.get(isbn) != null;
    }

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existsByIsbn(isbn)? Optional.of(books.get(isbn)) : Optional.empty();
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
        return book;
    }
    
}
