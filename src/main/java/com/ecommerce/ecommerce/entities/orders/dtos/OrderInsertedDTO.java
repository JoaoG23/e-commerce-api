package com.ecommerce.ecommerce.entities.orders.dtos;

import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.enums.OrderState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class OrderInsertedDTO{

	@NotBlank
	private String userId;

	@NotBlank
	private String methodPayment;

	private List<ItemInsertedDTO> items;

	@Enumerated(EnumType.STRING)
	private OrderState orderState;
}
