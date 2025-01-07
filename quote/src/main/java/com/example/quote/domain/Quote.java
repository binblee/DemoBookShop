package com.example.quote.domain;

public record Quote(
    String content,
    String author,
    Genre genre
) {
    
}
