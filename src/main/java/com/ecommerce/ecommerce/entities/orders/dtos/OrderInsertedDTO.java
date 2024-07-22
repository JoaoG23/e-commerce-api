package com.ecommerce.ecommerce.entities.orders.dtos;

import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderInsertedDTO{

	@NotBlank
	private String userId;

	@NotBlank
	private String methodPayment;

	@NotNull
	private List<OrderItemDTO> items;
}
