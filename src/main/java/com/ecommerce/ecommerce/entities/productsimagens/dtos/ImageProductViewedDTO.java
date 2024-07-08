package com.ecommerce.ecommerce.entities.productsimagens.dtos;

import com.ecommerce.ecommerce.entities.products.model.ProductModel;

public record ImageProductViewedDTO(
		String id,
		String path,
		ProductModel product
) {
}
