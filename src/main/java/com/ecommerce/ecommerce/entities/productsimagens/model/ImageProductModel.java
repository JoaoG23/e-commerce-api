package com.ecommerce.ecommerce.entities.productsimagens.model;

import com.ecommerce.ecommerce.entities.products.model.ProductModel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "images_product")
public class ImageProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String path;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductModel product;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
