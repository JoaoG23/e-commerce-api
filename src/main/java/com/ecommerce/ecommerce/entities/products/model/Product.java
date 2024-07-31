package com.ecommerce.ecommerce.entities.products.model;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;
import com.ecommerce.ecommerce.entities.stock.model.Stock;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString(exclude = {"imagesProduct", "stock", "ordersItems"})
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name_product", nullable = false)
	private String name;

	private BigDecimal price;

	@Column(length = 1000)
	private String details;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ImageProduct> imagesProduct;

	@OneToOne(mappedBy = "product")
	@JsonBackReference
	private Stock stock;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderItem> ordersItems;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
