package com.example.bookshop.order.config;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "demobookshop")
public record ClientProperties(
    @NotNull
    URI catalogServiceUri
) {
}
