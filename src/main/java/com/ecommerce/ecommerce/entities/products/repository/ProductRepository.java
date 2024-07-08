package com.ecommerce.ecommerce.entities.products.repository;

import com.ecommerce.ecommerce.entities.products.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, String> {}
