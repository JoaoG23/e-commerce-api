package com.ecommerce.ecommerce.entities.products.controllers;


import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.dtos.ProductViewedDTO;
import com.ecommerce.ecommerce.entities.products.services.ProductServices;
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
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductServices productServices;

	@PostMapping
	public ResponseEntity<String> createOne(@RequestBody @Valid ProductCreatedDTO productDTO) {
		productServices.create(productDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body("Product created");
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateById(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductCreatedDTO productDTO) {
		productServices.updateById(id, productDTO);
		return ResponseEntity.ok().body("Product updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {
		productServices.deleteById(id);
		return ResponseEntity.ok().body("Product deleted: " + id);
	}

	@GetMapping
	public ResponseEntity<List<ProductViewedDTO>> findAll() {
		var products = productServices.findAll();
		return ResponseEntity.ok().body(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductViewedDTO> findOneById(@PathVariable(value = "id") UUID id) {
		var product = productServices.findById(id);
		return (ResponseEntity<ProductViewedDTO>) ResponseEntity.ok().body(product);
	}

	@GetMapping("page")
	public ResponseEntity<Page<ProductViewedDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = productServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}
