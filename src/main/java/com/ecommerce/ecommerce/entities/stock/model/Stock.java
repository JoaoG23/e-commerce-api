package com.ecommerce.ecommerce.entities.stock.model;

import com.ecommerce.ecommerce.entities.products.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString(exclude = "product")
@Table(name = "stock_products")
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "products_id", referencedColumnName = "id")
	@JsonBackReference
	private Product product;

	private Integer quantity;

	@Column(name = "lot_price")
	private Double lotPrice;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
