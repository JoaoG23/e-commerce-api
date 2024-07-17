package com.ecommerce.ecommerce.entities.products.dtos;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductImageCreatedDTO(
		String id,
		@NotBlank(message = "Name is mandatory")  String name,
		@NotNull(message = "Price is mandatory")  BigDecimal price,
		@NotBlank(message = "Details is mandatory")  String details,

		List<ImageProductCreatedDTO> images
) {}
