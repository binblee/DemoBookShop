package com.example.quote.domain;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuoteService {
    private static final Random random = new Random();
    private static final List<Quote> quotes = List.of(
        new Quote("The way to get started is to quit talking and begin doing.", "Walt Disney", Genre.INSPIRATIONAL),
        new Quote("Life is what happens when you're busy making other plans.", "John Lennon", Genre.INSPIRATIONAL),
        new Quote("Adventure is an attitude we must apply to the day to day obstacles of life.", "John Amat", Genre.ADVENTURE),
        new Quote("Life is either a daring adventure or nothing at all.", "Helen Keller", Genre.ADVENTURE),
        new Quote("The only way of finding the limits of the possible is by going beyond them into the impossible.", "Arthur C. Clarke", Genre.FANTASY),
        new Quote("Fantasy is hardly an escape from reality. It's a way of understanding it.", "Lloyd Alexander", Genre.FANTASY)
    );

    public Flux<Quote> getAllQuotes(){
        return Flux.fromIterable(quotes);
    }

    public Mono<Quote> getRandomQuote(){
        return Mono.just(quotes.get(random.nextInt(quotes.size() - 1)));
    }

    public Mono<Quote> getRandomQuoteByGene(Genre genre){
        var quotesForGenre = quotes.stream()
            .filter(q -> q.genre().equals(genre))
            .toList();
        return Mono.just(quotesForGenre.get(random.nextInt(quotesForGenre.size() - 1)));
    }
}
