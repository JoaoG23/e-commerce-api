package com.ecommerce.ecommerce.entities.orders.dtos;

import jakarta.validation.constraints.NotBlank;

public record OrderInsertedDTO(
		@NotBlank String userId,
		@NotBlank String methodPayment
) {
}
