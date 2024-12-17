package com.example.bookshop.catalog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.example.bookshop.catalog.domain.Book;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        var book = new Book(13L, "1234567890", "Title", "Author", 9.99, now, now, 23);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2024-09-01T00:00:00Z");
        var content = """
                {
                    "id": 12,
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.99,
                    "createdDate": "2024-09-01T00:00:00Z",
                    "lastModifiedDate": "2024-09-01T00:00:00Z",
                    "version": 21
                }
                """;
        assertThat(json.parse(content)).usingRecursiveComparison()
            .isEqualTo(new Book(12L, "1234567890", "Title", "Author", 9.99, instant, instant, 21));
    }
}
