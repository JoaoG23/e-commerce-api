package com.ecommerce.ecommerce.entities.products.dtos;

import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;

import java.math.BigDecimal;
import java.util.List;


public record ProductViewedDTO(
		String id,
		String name,
		BigDecimal price,
		String details,
		List<ImageProduct> imagesProduct) {
}
