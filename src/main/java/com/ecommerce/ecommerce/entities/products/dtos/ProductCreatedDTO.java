package com.ecommerce.ecommerce.entities.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreatedDTO(
		String id,
		@NotBlank(message = "Name is mandatory") String name,
		@NotNull(message = "Price is mandatory") BigDecimal price,
		@NotBlank(message = "Details is mandatory") String details
) {
}
