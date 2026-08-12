package com.lets_plat.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank @Size(min = 3, max = 15, message = "Name must be between 3 and 15 charachter") String name,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6, message = "Password must be more than  6  charachters") String password

) {

}
