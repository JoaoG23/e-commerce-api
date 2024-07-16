package com.ecommerce.ecommerce.entities.stock.repository;

import com.ecommerce.ecommerce.entities.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
	@Query(value = "SELECT * FROM public.stock_products s WHERE s.products_id = :products_id", nativeQuery = true)
	List<Stock> findByProductsId(@Param("products_id") String productId);

	@Modifying
	@Query(value = "DELETE FROM public.stock_products p WHERE p.products_id =:products_id", nativeQuery = true)
	void deleteByProductsId(@Param("products_id") String productId);
}
