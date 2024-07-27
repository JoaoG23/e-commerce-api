package com.ecommerce.ecommerce.entities.orders.dtos;

import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemInsertedDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrderInsertedDTO{

	@NotBlank
	private String userId;

	@NotBlank
	private String methodPayment;

	private List<OrderItemInsertedDTO> items;
}
