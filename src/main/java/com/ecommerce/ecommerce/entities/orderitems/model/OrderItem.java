package com.ecommerce.ecommerce.entities.orderitems.model;

import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.products.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "order_items")
@ToString(exclude = "order")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference
	private Product product;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = true)
	@JsonBackReference
	private Order order;

	private Integer quantity;
}
