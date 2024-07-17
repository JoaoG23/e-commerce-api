package com.ecommerce.ecommerce.entities.stock.controllers;

import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockIncreaseDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockViewedDTO;
import com.ecommerce.ecommerce.entities.stock.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockService stockService;

	@PostMapping
	public ResponseEntity<ItemStockCreatedDTO> createOne(@RequestBody ItemStockCreatedDTO item) {
		return new ResponseEntity<ItemStockCreatedDTO>(stockService.create(item), HttpStatus.CREATED);
	}

	@PatchMapping("/increase-decrease")
	public ResponseEntity<ItemStockIncreaseDTO> selectIncreaseOrDecreaseProduct(@RequestBody ItemStockIncreaseDTO item) {
		return ResponseEntity.ok(stockService.selectIncreaseOrDecreaseProduct(item));
	}



	@GetMapping("page")
	public ResponseEntity<Page<ItemStockViewedDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = stockService.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ItemStockViewedDTO> findOneByProductId(@PathVariable String productId) {
		return ResponseEntity.ok().body(stockService.findByProductId(productId));
	}
}
