package com.ecommerce.ecommerce.entities.orders.dtos;

import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public record OrderDTO(
		Optional<String> id,

		String userId,
		String methodPayment,

		BigDecimal totalPrice,
		Optional<List<OrderItem>> items

) {
}
