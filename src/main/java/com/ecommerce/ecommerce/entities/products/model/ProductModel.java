package com.ecommerce.ecommerce.entities.products.model;


import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProductModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
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

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ImageProductModel> imagesProduct;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}


