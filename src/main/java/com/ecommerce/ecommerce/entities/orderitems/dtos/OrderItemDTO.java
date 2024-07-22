package com.ecommerce.ecommerce.entities.orderitems.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
public class OrderItemDTO {

	private Optional<String> id;
	@NotBlank
	private String productId;
	private String orderId;
	@NotBlank
	private Integer quantity;
}
