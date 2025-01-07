package com.example.bookshop.quote_service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.quote_service.domain.Genre;
import com.example.bookshop.quote_service.domain.Quote;
import com.example.bookshop.quote_service.domain.QuoteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class QuoteServiceController {
    private final QuoteService quoteService;

    public QuoteServiceController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/quotes")
    public Flux<Quote> getAllQuotes() {
        return quoteService.getAllQuotes();
    }

    @GetMapping("/quotes/random")
    public Mono<Quote> getRandomQuote() {
        return quoteService.getRandomQuote();
    }
    
    @GetMapping("/quotes/random/{genre}")
    public Mono<Quote> getRandomQuoteByGenre(@PathVariable Genre genre) {
        return quoteService.getRandomQuoteByGene(genre);
    }
    
}
