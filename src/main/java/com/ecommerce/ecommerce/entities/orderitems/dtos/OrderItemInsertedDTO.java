package com.ecommerce.ecommerce.entities.orderitems.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Optional;

@Data
@ToString
public class OrderItemInsertedDTO {

	private Optional<String> id;
	@NotBlank
	private String productId;
	@NotBlank
	private String orderId;
	@NotNull
	private Integer quantity;
}
