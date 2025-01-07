package com.example.quote;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.quote.domain.Genre;
import com.example.quote.domain.Quote;
import com.example.quote.domain.QuoteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class QuoteFunctions {
    private static final Logger log = LoggerFactory.getLogger(QuoteFunctions.class);
    
    @Bean
    Supplier<Flux<Quote>> allQuotes(QuoteService quoteService){
        return () -> {
            log.info("Request for all quotes");
            return quoteService.getAllQuotes();
        };
    }

    @Bean
    Supplier<Mono<Quote>> randomQuote(QuoteService quoteService){
        return () -> {
            log.info("Request for random quote");
            return quoteService.getRandomQuote();
        };
    }

    @Bean
    Function<Mono<Genre>, Mono<Quote>> genreQuote(QuoteService quoteService){
        return mono -> mono.flatMap(genre -> {
            log.info("Request for random quote by genre: {}", genre);
            return quoteService.getRandomQuoteByGene(genre);
        });
    }

    @Bean
    Consumer<Quote> logQuote(){
        return quote -> log.info("Quote: {} by {} ({})", quote.content(), quote.author(), quote.genre());
    }
}
