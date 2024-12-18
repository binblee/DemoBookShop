package com.example.bookshop.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookshop.catalog.config.DataConfig;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234567890";
        var book = Book.of(bookIsbn, "Title", "Author", 9.99, null);
        jdbcAggregateTemplate.insert(book);
        Optional<Book> foundBook = bookRepository.findByIsbn(bookIsbn);
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().isbn()).isEqualTo(bookIsbn);
    }

    @Test
    void findAllBooks() {
        var book1 = Book.of("1234567890", "Title1", "Author1", 9.99, "");
        var book2 = Book.of("2345678901", "Title2", "Author2", 19.99, "");
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

        Iterable<Book> books = bookRepository.findAll();
        assertThat(StreamSupport.stream(books.spliterator(), true)
            .filter(book -> book.isbn().equals(book1.isbn()) || book.isbn().equals(book2.isbn()))
            .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        var bookIsbn = "1234567890";
        Optional<Book> foundBook = bookRepository.findByIsbn(bookIsbn);
        assertThat(foundBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        var bookIsbn = "1234567890";
        var book = Book.of(bookIsbn, "Title", "Author", 9.99, "");
        jdbcAggregateTemplate.insert(book);
        assertThat(bookRepository.existsByIsbn(bookIsbn)).isTrue();
    }

    @Test
    void existsByIsbnWhenNotExisting() {
        assertThat(bookRepository.existsByIsbn("1234567890")).isFalse();
    }

    @Test
    void deleteByIsbn() {
        var isbn = "1234567890";
        var book = Book.of(isbn, "Title", "Author", 13.99, "");
        var bookPersisted = jdbcAggregateTemplate.insert(book);
        bookRepository.deleteByIsbn(isbn);

        assertThat(bookRepository.existsById(bookPersisted.id())).isFalse();
    }
}
