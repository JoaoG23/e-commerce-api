package com.ecommerce.ecommerce.entities.users.authentication.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank(message = "Email is mandatory") String email,
                              @NotBlank(message = "Password is mandatory") String password) {
}
