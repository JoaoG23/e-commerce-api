package com.ecommerce.ecommerce.entities.users.authentication.dtos;

import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
		@NotBlank(message = "Name is mandatory") String name,
		@NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,
		@NotBlank(message = "Password is mandatory") String password,
		@NotNull(message = "Role is mandatory")
		@Enumerated(EnumType.STRING)
		UserRole role
		) {
}