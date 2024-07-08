package com.ecommerce.ecommerce.entities.productsimagens.controllers;

import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductViewedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.services.ImageProductServices;
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
@RequestMapping("/products/image-products")
public class ImageProductController {
	@Autowired
	private ImageProductServices imageProductServices;

	@PostMapping
	public ResponseEntity<String> createOne(@RequestBody @Valid ImageProductCreatedDTO imageProductDTO) {
		imageProductServices.create(imageProductDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Image created");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateById(@PathVariable(value = "id") String id, @RequestBody @Valid ImageProductCreatedDTO imageProductDTO) {
		imageProductServices.updateById(id, imageProductDTO);
		return ResponseEntity.ok().body("Image updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		imageProductServices.deleteById(id);
		return ResponseEntity.ok().body("Image deleted: " + id);
	}

	@GetMapping
	public ResponseEntity<List<ImageProductViewedDTO>> findAll() {
		List<ImageProductViewedDTO> imageProducts = imageProductServices.findAll();
		return ResponseEntity.ok().body(imageProducts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ImageProductViewedDTO> findOneById(@PathVariable(value = "id") String id) {
		ImageProductViewedDTO imageProduct = imageProductServices.findById(id);
		return (ResponseEntity<ImageProductViewedDTO>) ResponseEntity.ok().body(imageProduct);
	}

	@GetMapping("page")
	public ResponseEntity<Page<ImageProductViewedDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = imageProductServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}
