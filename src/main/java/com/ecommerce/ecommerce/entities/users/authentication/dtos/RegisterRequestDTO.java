package com.ecommerce.ecommerce.entities.users.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
		@NotBlank(message = "Name is mandatory") String name,
		@NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,
		@NotBlank(message = "Password is mandatory") String password
) {
}