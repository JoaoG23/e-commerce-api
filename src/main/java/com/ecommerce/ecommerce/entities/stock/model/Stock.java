package com.ecommerce.ecommerce.entities.stock.model;

import com.ecommerce.ecommerce.entities.products.model.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name = "stock_products")
@Getter
@Setter
@Entity
@ToString(exclude = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "products_id", referencedColumnName = "id")

	private Product product;
	private Integer quantity;

	@Column(name = "lot_price")
	private Double lotPrice;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

}
