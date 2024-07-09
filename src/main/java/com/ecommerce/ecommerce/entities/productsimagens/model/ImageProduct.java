package com.ecommerce.ecommerce.entities.productsimagens.model;

import com.ecommerce.ecommerce.entities.products.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "images_product")
public class ImageProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String path;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
