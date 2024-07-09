package com.ecommerce.ecommerce.entities.productsimagens.dtos;

import com.ecommerce.ecommerce.entities.products.model.Product;

public record ImageProductViewedDTO(
		String id,
		String path,
		Product product
) {
}
