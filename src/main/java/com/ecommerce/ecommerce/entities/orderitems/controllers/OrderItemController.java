package com.ecommerce.ecommerce.entities.orderitems.controllers;

import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemQueryDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.services.OrderItemServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/items")
public class OrderItemController {
	@Autowired
	private OrderItemServices orderItemServices;

	@PostMapping
	public ResponseEntity<OrderItem> createOne(@RequestBody @Valid ItemInsertedDTO orderItemDTO) {
		OrderItem orderItem = orderItemServices.create(orderItemDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateById(@PathVariable(value = "id") String id, @RequestBody @Valid ItemInsertedDTO orderItemDTO) {
		OrderItem orderItem = orderItemServices.updateById(id, orderItemDTO);
		return ResponseEntity.ok().body(orderItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		orderItemServices.deleteById(id);
		return ResponseEntity.ok().body("OrderItem deleted: " + id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemQueryDTO> findOneById(@PathVariable(value = "id") String id) {
		ItemQueryDTO orderItem = orderItemServices.findById(id);
		return (ResponseEntity<ItemQueryDTO>) ResponseEntity.ok().body(orderItem);
	}
	@GetMapping
	public ResponseEntity<List<ItemQueryDTO>> findAll() {
		return ResponseEntity.ok().body(orderItemServices.findAll());
	}
}
