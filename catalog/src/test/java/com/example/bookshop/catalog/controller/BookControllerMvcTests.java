package com.example.bookshop.catalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bookshop.catalog.domain.BookService;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenReturn404() throws Exception {
        String isbn = "1234567890";
        mockMvc.perform(get("/books/", isbn)).andExpect(status().isNotFound());
    }

}
