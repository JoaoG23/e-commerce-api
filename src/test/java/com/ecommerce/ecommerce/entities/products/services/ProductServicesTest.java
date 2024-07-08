package com.ecommerce.ecommerce.entities.products.services;

import com.ecommerce.ecommerce.entities.products.dtos.ProductImageCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.repository.ImageProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ProductServicesTest {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ImageProductRepository imageProductRepository;

	@Autowired
	@InjectMocks
	private ProductServices productServices;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Create product with multiple images")
	void createProductWithImagesCaseOne() {

		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
		images.add(new ImageProductCreatedDTO(null, "C://", null));
		var product = new ProductImageCreatedDTO("id", "name", new BigDecimal("10.20"), "Detalis", images);


		when(productRepository.save(any())).thenReturn(product);
		productServices.createOneWithImages(product);


		verify(productRepository, times(1)).save(any());
		verify(imageProductRepository, times(1)).save(any());
	}
}