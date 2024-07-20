package com.ecommerce.ecommerce.entities.orders.controllers;

import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.services.OrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<OrderDTO> orders = orderServices.findAll();
		return ResponseEntity.ok().body(orders);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findOneById(@PathVariable(value = "id") String id) {
		OrderDTO order = orderServices.findById(id);
		return (ResponseEntity<OrderDTO>) ResponseEntity.ok().body(order);
	}

	@GetMapping("page")
	public ResponseEntity<Page<OrderDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = orderServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}
