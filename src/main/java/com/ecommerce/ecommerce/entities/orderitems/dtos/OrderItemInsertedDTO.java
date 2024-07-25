package com.ecommerce.ecommerce.entities.orderitems.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.Optional;

@Data
@ToString
public class OrderItemInsertedDTO {

	private Optional<String> id;
	@NotBlank
	private String productId;
	private String orderId;
	@NotBlank
	private Integer quantity;
}
