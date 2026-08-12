package com.user.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EditUserRequestDto(
        @NotBlank @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "Role must be either ADMIN or USER") String Role) {
}