package com.ecommerce.ecommerce.entities.orders.dtos;

import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public record OrderDTO(
		@NotBlank Optional<String> id,

		@NotBlank String userId,
		@NotBlank String methodPayment,

		@NotBlank BigDecimal totalPrice,
		@NotBlank Optional<List<OrderItem>> items

) {
}
