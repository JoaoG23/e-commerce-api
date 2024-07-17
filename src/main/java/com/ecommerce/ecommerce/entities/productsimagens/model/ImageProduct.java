package com.ecommerce.ecommerce.entities.productsimagens.model;

import com.ecommerce.ecommerce.entities.products.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@ToString(exclude = "product")
@Table(name = "images_product")
public class ImageProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private String path;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = true)
	@JsonBackReference
	private Product product;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
