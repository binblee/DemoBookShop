package com.example.bookshop.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
    @NotBlank(message = "The book ISBN must be defined")
    String isbn,
    
    @NotNull(message="The book quantity must be defined")
    @Min(value=1, message="The book quantity must be greater than 0")
    @Max(value=5, message="The book quantity must be less than or equal to 5") 
    Integer quantity
) {
    
}
