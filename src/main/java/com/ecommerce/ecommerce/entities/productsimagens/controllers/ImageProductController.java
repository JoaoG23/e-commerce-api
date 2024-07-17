package com.ecommerce.ecommerce.entities.productsimagens.controllers;

import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductViewedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.services.ImageProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/image-products")
public class ImageProductController {
	@Autowired
	private ImageProductServices imageProductServices;
	@PostMapping
	public ResponseEntity<String> createOne(@RequestBody @Valid ImageProductCreatedDTO imageProductDTO) {
		var imageProduct = imageProductServices.create(imageProductDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(imageProduct.getId());
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		imageProductServices.deleteById(id);
		return ResponseEntity.ok().body("Image of product deleted: " + id);
	}
	@GetMapping("/{id}")
	public ResponseEntity<ImageProductViewedDTO> findOneById(@PathVariable(value = "id") String id) {
		ImageProductViewedDTO imageProduct = imageProductServices.findById(id);
		return (ResponseEntity<ImageProductViewedDTO>) ResponseEntity.ok().body(imageProduct);
	}
}
