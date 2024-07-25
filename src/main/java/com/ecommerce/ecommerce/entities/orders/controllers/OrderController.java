package com.ecommerce.ecommerce.entities.orders.controllers;

import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.services.OrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderServices orderServices;

	@PostMapping
	public ResponseEntity<Order> createOne(@RequestBody @Valid OrderInsertedDTO orderDTO) {
		var order = orderServices.create(orderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Order> updateById(@PathVariable(value = "id") String id, @RequestBody @Valid OrderInsertedDTO orderDTO) {
		Order order = orderServices.updateById(id, orderDTO);
		return ResponseEntity.ok().body(order);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		orderServices.deleteById(id);
		return ResponseEntity.ok().body("Order deleted: " + id);
	}

	@GetMapping("/orders-items/{orderId}")
	public ResponseEntity<List<OrderDTO>> findOrderItemsByOrderId(@PathVariable(value = "orderId") String orderId) {
		List<OrderDTO> items = orderServices.findOrderItemsByOrderId(orderId);
		return ResponseEntity.ok().body(items);
	}

	@GetMapping
	public ResponseEntity<List<OrderDTO>> findOrderItemsAll() {
		List<OrderDTO> items = orderServices.findOrderItemsAll();
		return ResponseEntity.ok().body(items);
	}
}
