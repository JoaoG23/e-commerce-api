package com.ecommerce.ecommerce.entities.products.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@ToString
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	private BigDecimal price;
	private String details;
	private String telephone;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}


