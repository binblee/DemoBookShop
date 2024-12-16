package com.example.bookshop.catalog.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds(){
        Book book = new Book("9783161484100", "The Catcher in the Rye", "J.D. Salinger", 8.99);
        assert(validator.validate(book).isEmpty());
    }

    @Test
    void whenIsbnNotDefinedThenValidationFails(){
        Book book = new Book(null, "The Catcher in the Rye", "J.D. Salinger", 8.99);
        assert(validator.validate(book).size() == 1);
    }

    @Test
    void whenIsbnDefinedButInvalidThenValidationFails(){
        Book book = new Book("978316148410x", "The Catcher in the Rye", "J.D. Salinger", 8.99);
        assert(validator.validate(book).size() == 1);
    }

    @Test
    void whenTitleNotDefinedThenValidationFails(){
        Book book = new Book("9783161484100", null, "J.D. Salinger", 8.99);
        assert(validator.validate(book).size() == 1);
    }

    @Test
    void whenAuthorNotDefinedThenValidationFails(){
        Book book = new Book("9783161484100", "The Catcher in the Rye", null, 8.99);
        assert(validator.validate(book).size() == 1);
    }

    @Test
    void whenPriceNotDefinedThenValidationFails(){
        Book book = new Book("9783161484100", "The Catcher in the Rye", "J.D. Salinger", null);
        assert(validator.validate(book).size() == 1);
    }

    @Test
    void whenPriceDefinedButNegativeThenValidationFails(){
        Book book = new Book("9783161484100", "The Catcher in the Rye", "J.D. Salinger", -8.99);
        assert(validator.validate(book).size() == 1);
    }

}
