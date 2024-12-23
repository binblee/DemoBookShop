package com.example.bookshop.order.book;

public record Book(
    String isbn,
    String title,
    String author,
    double price
) {
    
}
