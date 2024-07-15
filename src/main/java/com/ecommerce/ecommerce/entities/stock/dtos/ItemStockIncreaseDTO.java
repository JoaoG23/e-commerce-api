package com.ecommerce.ecommerce.entities.stock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemStockIncreaseDTO(
		@NotNull
		Boolean increase ,
		@NotNull(message = "Quantity is mandatory") Integer quantity,
		@NotBlank(message = "Id Product is mandatory") String productId
) {
}
