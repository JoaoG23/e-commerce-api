package com.ecommerce.ecommerce.entities.orders.model;

import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orders.enums.OrderState;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orderItems")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	@JsonBackReference
	private User user;

	private String methodPayment;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderItem> orderItems;

	@Enumerated(EnumType.STRING)
	private OrderState orderState;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
