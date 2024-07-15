package com.ecommerce.ecommerce.entities.stock.dtos;

public record ItemStockViewedDTO(
		String id,
		String productId,
		Integer quantity,
		Double lotPrice
) {
}
