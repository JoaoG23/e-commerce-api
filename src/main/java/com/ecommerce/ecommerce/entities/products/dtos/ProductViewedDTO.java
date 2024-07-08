package com.ecommerce.ecommerce.entities.products.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductViewedDTO {
	public UUID id;
	public String name;
	public BigDecimal price;
	public String details;
	public String telephone;
}
