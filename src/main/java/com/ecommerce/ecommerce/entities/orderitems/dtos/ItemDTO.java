package com.ecommerce.ecommerce.entities.orderitems.dtos;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.products.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
public class ItemDTO {

	private Optional<String> id;
	@NotBlank
	private Product product;
	private Order order;
	@NotBlank
	private Integer quantity;
}
