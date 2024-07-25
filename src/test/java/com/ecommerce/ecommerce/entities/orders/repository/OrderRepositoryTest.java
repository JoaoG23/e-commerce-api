package com.ecommerce.ecommerce.entities.orders.repository;

import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.stock.model.Stock;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	@BeforeEach
	public void tearDown() {
		stockRepository.deleteAll();
		productRepository.deleteAll();
	}

	@Test
	@DisplayName("Should get stock item of idProduct from database")
	public void findByProductsId() {

		Product product = new Product();
		product.setName("Test Product");
		product = productRepository.save(product);

		// Create and save a stock item for the product
		Stock stock = new Stock();
		stock.setProduct(product);
		stock.setQuantity(10);
		stock.setLotPrice(100.0);
		stockRepository.save(stock);

		List<Stock> stocks = stockRepository.findByProductsId(product.getId());

		assertNotNull(stocks);
		assertEquals(1, stocks.size());
		assertEquals(product.getId(), stocks.get(0).getProduct().getId());
	}

	@Test
	@DisplayName("Should delete stock item of idProduct from database")
	@Transactional
	public void deleteByProductsId() {

		var product = new Product();
		product.setName("Test Product");
		product = productRepository.save(product);

		// Create and save a stock item for the product
		Stock stock = new Stock();
		stock.setProduct(product);
		stock.setQuantity(10);
		stock.setLotPrice(100.0);
		stockRepository.save(stock);

		String productId = product.getId();
		stockRepository.deleteByProductsId(productId);
		List<Stock> stocks = stockRepository.findByProductsId(productId);

		assertNotNull(stocks);
		assertTrue(stocks.isEmpty());
	}
}
