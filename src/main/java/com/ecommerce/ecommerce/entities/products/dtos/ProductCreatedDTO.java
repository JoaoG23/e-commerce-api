package com.ecommerce.ecommerce.entities.products.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductCreatedDTO {

	public UUID id;
	@NotEmpty
	public String name;
	@NotEmpty
	public BigDecimal price;
	@NotNull
	public String details;
	@NotEmpty
	public String telephone;
}
