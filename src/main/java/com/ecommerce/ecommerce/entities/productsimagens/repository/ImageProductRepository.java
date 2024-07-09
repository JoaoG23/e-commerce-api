package com.ecommerce.ecommerce.entities.productsimagens.repository;

import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, String> {

	void deleteAllByProductId(String id);
}