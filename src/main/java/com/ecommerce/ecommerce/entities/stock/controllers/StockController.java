package com.ecommerce.ecommerce.entities.stock.controllers;

import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockIncreaseDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockViewedDTO;
import com.ecommerce.ecommerce.entities.stock.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockService stockService;

	@PostMapping
	public ResponseEntity<ItemStockCreatedDTO> createOne(@RequestBody ItemStockCreatedDTO item) {
		return new ResponseEntity<ItemStockCreatedDTO>(stockService.create(item), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ItemStockCreatedDTO> updateById(@PathVariable String id, @RequestBody ItemStockCreatedDTO stockDetails) {
		return ResponseEntity.ok(stockService.update(id, stockDetails));
	}

	@PatchMapping("/increase-decrease")
	public ResponseEntity<ItemStockIncreaseDTO> selectIncreaseOrDecreaseProduct(@RequestBody ItemStockIncreaseDTO item) {
		return ResponseEntity.ok(stockService.selectIncreaseOrDecreaseProduct(item));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		stockService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ItemStockViewedDTO>> findAll() {
		return ResponseEntity.ok().body(stockService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemStockViewedDTO> findOneById(@PathVariable String id) {
		return ResponseEntity.ok().body(stockService.findById(id));
	}
}
