package com.ecommerce.ecommerce.entities.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreatedDTO(
		@NotBlank(message = "ID is mandatory") String id,
		@NotBlank(message = "Name is mandatory") String name,
		@NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,
		@NotBlank(message = "Password is mandatory") String password
) {
}

