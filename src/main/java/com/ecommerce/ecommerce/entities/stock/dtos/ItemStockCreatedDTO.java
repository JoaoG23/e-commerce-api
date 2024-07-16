package com.ecommerce.ecommerce.entities.stock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemStockCreatedDTO(
		String id,
		@NotBlank(message = "Product is mandatory") String productId,
		@NotNull(message = "Quantity is mandatory") Integer quantity,
		@NotBlank(message = "Lot price is mandatory") Double lotPrice
) {
}
