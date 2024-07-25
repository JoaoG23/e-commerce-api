package com.ecommerce.ecommerce.entities.orders.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {
	private String orderId;
	private String methodPayment;
	private BigDecimal totalPrice;
	private LocalDateTime updatedAt;
	private String userId;
	private String id;
	private Integer quantity;
	private String productId;
	private String details;
	private String nameProduct;
	private BigDecimal price;
	private BigDecimal totalPriceItem;

	public OrderDTO(String orderId, String methodPayment, BigDecimal totalPrice, LocalDateTime updatedAt, String userId, String id, Integer quantity, String productId, String details, String nameProduct, BigDecimal price, BigDecimal totalPriceItem) {
		this.orderId = orderId;
		this.methodPayment = methodPayment;
		this.totalPrice = totalPrice;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.id = id;
		this.quantity = quantity;
		this.productId = productId;
		this.details = details;
		this.nameProduct = nameProduct;
		this.price = price;
		this.totalPriceItem = totalPriceItem;
	}
}

