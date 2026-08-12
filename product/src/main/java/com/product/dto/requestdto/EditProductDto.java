package com.product.dto.requestdto;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EditProductDto(
        @Size(min = 3, max = 15, message = "Product Name must be between 3, 15 chars") String name,
         @Size(min = 3, max = 150, message = "Product descriptio must be between 3, 150 chars") String description,
        @Positive(message = "Price must be greater than zero") Double price) {
}
