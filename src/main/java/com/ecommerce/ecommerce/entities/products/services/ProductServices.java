package com.ecommerce.ecommerce.entities.products.services;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.dtos.ProductViewedDTO;
import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;
import com.ecommerce.ecommerce.entities.productsimagens.repository.ImageProductRepository;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class ProductServices {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ImageProductRepository imageProductRepository;

	public Product create(ProductCreatedDTO productDTO) {
		Product product = new Product();

		BeanUtils.copyProperties(productDTO, product);
		Product productCreated = this.productRepository.save(product);

		return this.createImagesProduct(productCreated, productDTO);
	}


	@Transactional
	public Product createImagesProduct(Product product, ProductCreatedDTO productDTO) {

		String idProduct = product.getId();
		var images = new ArrayList<ImageProductCreatedDTO>(productDTO.imageProduct());

		Boolean isNotHaveImages = productDTO.imageProduct().isEmpty();
		if (isNotHaveImages) {
			return product;
		}
		images.forEach(image -> {
			ImageProductCreatedDTO imageWithIdProduct = new ImageProductCreatedDTO(null, image.getPath(), idProduct);
			ImageProduct imageProduct = new ImageProduct();

			BeanUtils.copyProperties(imageWithIdProduct, imageProduct);
			imageProduct.setProduct(product);

			this.imageProductRepository.save(imageProduct);
		});
		return product;
	}


	@Transactional
	public Product updateById(String id, ProductCreatedDTO productDTO) {
		validateIfProductNotExistsById(id);
		Product product = new Product();
		BeanUtils.copyProperties(productDTO, product);
		product.setId(id);
		return productRepository.save(product);
	}

	public List<ProductViewedDTO> findAll() {
		List<Product> products = productRepository.findAll();
		List<ProductViewedDTO> productDTOs = new ArrayList<>();
		for (Product product : products) {
			ProductViewedDTO productDTO = convertModelToProductViewedDTO(product);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	public Page<ProductViewedDTO> findAllByPage(Pageable pageable) {
		Page<Product> pages = productRepository.findAll(pageable);

		List<ProductViewedDTO> productDTOs = pages.getContent().stream().map(this::convertModelToProductViewedDTO).collect(Collectors.toList());
		return new PageImpl<>(productDTOs, pageable, pages.getTotalElements());
	}

	public ProductViewedDTO findById(String id) {
		validateIfProductNotExistsById(id);

		Optional<Product> productModelOptional = productRepository.findById(id);
		Product product = productModelOptional.get();
		return convertModelToProductViewedDTO(product);
	}

	@Transactional
	public void deleteById(String id) {
		validateIfProductNotExistsById(id);
		stockRepository.deleteByProductsId(id);
		productRepository.deleteById(id);
	}

	private ProductViewedDTO convertModelToProductViewedDTO(Product product) {
		ProductViewedDTO productDTO = new ProductViewedDTO(
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getDetails(),
				product.getImagesProduct()
		);

		return productDTO;
	}

	private void validateIfProductNotExistsById(String id) {
		Optional<Product> productFound = productRepository.findById(id);
		if (productFound.isEmpty()) {
			throw new NotFoundCustomException("Product not found with id: " + id);
		}
	}
}