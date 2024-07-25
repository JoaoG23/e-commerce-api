package com.ecommerce.ecommerce.entities.orderitems.controllers;

import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.services.OrderItemServices;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/items")
public class OrderItemController {
	@Autowired
	private OrderItemServices orderItemServices;

	@PostMapping
	public ResponseEntity<OrderItem> createOne(@RequestBody @Valid OrderItemInsertedDTO orderItemDTO) {
		OrderItem orderItem = orderItemServices.create(orderItemDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateById(@PathVariable(value = "id") String id, @RequestBody @Valid OrderItemInsertedDTO orderItemDTO) {
		OrderItem orderItem = orderItemServices.updateById(id, orderItemDTO);
		return ResponseEntity.ok().body(orderItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		orderItemServices.deleteById(id);
		return ResponseEntity.ok().body("OrderItem deleted: " + id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findOneById(@PathVariable(value = "id") String id) {
		OrderDTO orderItem = orderItemServices.findById(id);
		return (ResponseEntity<OrderDTO>) ResponseEntity.ok().body(orderItem);
	}


}
