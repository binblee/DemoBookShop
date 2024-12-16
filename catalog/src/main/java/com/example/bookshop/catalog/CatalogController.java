package com.example.bookshop.catalog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.catalog.config.DemoBookShopProperties;

@RestController
public class CatalogController {
    private final DemoBookShopProperties dbsProperties;

    public CatalogController(DemoBookShopProperties dbsProperties) {
        this.dbsProperties = dbsProperties;
    }

    @GetMapping("/greeting")
    public String getGreeting(){
        return dbsProperties.getGreeting();
    }
}
