package com.ecommerce.ecommerce.entities.products.dtos;

import java.math.BigDecimal;



public record ProductViewedDTO(
		String id,
		String name,
		BigDecimal price,
		String details,
		java.util.List<com.ecommerce.ecommerce.entities.productsimagens.model.ImageProductModel> imagesProduct) {
}
