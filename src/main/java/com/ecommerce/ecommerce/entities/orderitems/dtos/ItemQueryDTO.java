package com.ecommerce.ecommerce.entities.orderitems.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class ItemQueryDTO {
	private String id;
	private String orderId;
	private String methodPayment;
	private String userId;
	private Integer quantity;
	private String productId;
	private String nameProduct;
	private BigDecimal price;
	private BigDecimal totalPriceItem;
}