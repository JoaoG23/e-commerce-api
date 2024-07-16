package com.ecommerce.ecommerce.entities.stock.services;

import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockIncreaseDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockViewedDTO;
import com.ecommerce.ecommerce.entities.stock.model.Stock;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.CustomException;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public ItemStockCreatedDTO create(ItemStockCreatedDTO itemDto) {
		var productFound = productRepository.findById(itemDto.productId()).orElseThrow(() -> new NotFoundCustomException("Product not found"));

		Stock item = new Stock();
		item.setProduct(productFound);
		item.setQuantity(itemDto.quantity());
		item.setLotPrice(Double.valueOf(itemDto.lotPrice()));

		stockRepository.save(item);
		return itemDto;
	}

	@Transactional
	public ItemStockIncreaseDTO selectIncreaseOrDecreaseProduct(ItemStockIncreaseDTO itemDto) {
		if (itemDto.increase()) {
			return increaseQuantityProduct(itemDto);
		}
		return decreaseQuantityProduct(itemDto);
	}

	@Transactional
	private ItemStockIncreaseDTO increaseQuantityProduct(ItemStockIncreaseDTO itemDto) {
		List<Stock> itemFound = stockRepository.findByProductsId(itemDto.productId());
		if (itemFound.isEmpty()) {
			throw new NotFoundCustomException("Item not found");
		}

		Stock stockItem = itemFound.get(0);
		Stock item = new Stock();

		int newQuantity = stockItem.getQuantity() + itemDto.quantity();

		stockItem.setQuantity(newQuantity);

		item.setProduct(productRepository.findById(itemDto.productId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));
		item.setQuantity(stockItem.getQuantity());
		item.setLotPrice(Double.valueOf(stockItem.getLotPrice()));
		item.setId(stockItem.getId());

		stockRepository.save(item);
		return itemDto;
	}

	@Transactional
	private ItemStockIncreaseDTO decreaseQuantityProduct(ItemStockIncreaseDTO itemDto) {
		List<Stock> itemFound = stockRepository.findByProductsId(itemDto.productId());
		if (itemFound.isEmpty()) {
			throw new NotFoundCustomException("Item not found");
		}

		Stock stockItem = itemFound.get(0);

		int newQuantity = stockItem.getQuantity() - itemDto.quantity();

		if (newQuantity < 0) {
			throw new CustomException("Don't have enough quantity in stock for decrease! Current quantity:" + stockItem.getQuantity());
		}

		stockItem.setQuantity(newQuantity);

		Stock item = new Stock();
		item.setProduct(productRepository.findById(itemDto.productId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));
		item.setQuantity(stockItem.getQuantity());
		item.setLotPrice(Double.valueOf(stockItem.getLotPrice()));
		item.setId(stockItem.getId());

		stockRepository.save(item);
		return itemDto;
	}

	public ItemStockViewedDTO findByProductId(String productId) {
		List<Stock> stockItem = stockRepository.findByProductsId(productId);
		if (stockItem.size() == 0) throw new NotFoundCustomException("Item not found");
		Stock stock = stockItem.get(0);
		return convertModelToDtoViewed(stock);
	}


	public Page<ItemStockViewedDTO> findAllByPage(Pageable pageable) {
		Page<Stock> pages = stockRepository.findAll(pageable);
		List<ItemStockViewedDTO> productDTOs = pages.getContent().stream().map(this::convertModelToDtoViewed).collect(Collectors.toList());
		return new PageImpl<>(productDTOs, pageable, pages.getTotalElements());
	}

	private ItemStockViewedDTO convertModelToDtoViewed(Stock product) {
		ItemStockViewedDTO productDTO = new ItemStockViewedDTO(
				product.getId(),
				product.getProduct().getId(),
				product.getQuantity(),
				product.getLotPrice()
		);
		return productDTO;
	}
}
