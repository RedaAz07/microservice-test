package com.lets_plat.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
        @NotBlank @Size(min = 3, max = 15, message = "Product Name must be between 3, 15 chars") String name,
        @NotBlank @Size(min = 3, max = 150, message = "Product descriptio must be between 3, 150 chars") String description,
        @NotNull(message = "Price cannot be missing") @Positive(message = "Price must be greater than zero") Double price) {
}
