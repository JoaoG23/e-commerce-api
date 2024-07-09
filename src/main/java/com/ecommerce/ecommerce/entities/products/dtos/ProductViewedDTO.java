package com.ecommerce.ecommerce.entities.products.dtos;

import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;

import java.math.BigDecimal;



public record ProductViewedDTO(
		String id,
		String name,
		BigDecimal price,
		String details,
		java.util.List<ImageProduct> imagesProduct) {
}
