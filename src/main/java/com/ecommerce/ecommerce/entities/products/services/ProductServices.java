package com.ecommerce.ecommerce.entities.products.services;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.dtos.ProductViewedDTO;
import com.ecommerce.ecommerce.entities.products.model.ProductModel;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException.NotFoundCustomException;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServices {
	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public ProductModel create(ProductCreatedDTO productDTO) {

		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDTO, productModel);
		return productRepository.save(productModel);
	}

	@Transactional
	public void updateById(UUID id, ProductCreatedDTO productDTO) {

		validateIfProductNotExistsById(id);

		ProductModel productModel = new ProductModel();
		productDTO.setId(id);
		BeanUtils.copyProperties(productDTO, productModel);

		productRepository.save(productModel);
	}

	public List<ProductViewedDTO> findAll() {

		List<ProductModel> products = productRepository.findAll();
		List<ProductViewedDTO> productDTOs = new ArrayList<>();

		for (ProductModel product : products) {
			ProductViewedDTO productDTO = convertModelToProductViewedDTO(product);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	public Page<ProductViewedDTO> findAllByPage(Pageable pageable) {
		Page<ProductModel> pages = productRepository.findAll(pageable);

		List<ProductViewedDTO> productDTOs = pages.getContent().stream()
				.map(this::convertModelToProductViewedDTO)
				.collect(Collectors.toList());

		return new PageImpl<>(productDTOs, pageable, pages.getTotalElements());
	}

	public ProductViewedDTO findById(UUID id) {
		validateIfProductNotExistsById(id);

		Optional<ProductModel> productModelOptional = productRepository.findById(id);
		ProductModel productModel = productModelOptional.get();
		return convertModelToProductViewedDTO(productModel);
	}

	public void deleteById(UUID id) {
		validateIfProductNotExistsById(id);
		productRepository.deleteById(id);
	}

	private ProductViewedDTO convertModelToProductViewedDTO(ProductModel productModel) {
		ProductViewedDTO productDTO = new ProductViewedDTO();

		// Crie setter e getters com o nome do atributo

		BeanUtils.copyProperties(productModel, productDTO);
//		productDTO.setId(productModel.getId());
//		productDTO.setName(productModel.getName());
//		productDTO.setPrice(productModel.getPrice());

		return productDTO;
	}

	private void validateIfProductNotExistsById(UUID id) {
		Optional<ProductModel> productFound = productRepository.findById(id);
		if (productFound.isEmpty()) {
			throw new NotFoundCustomException("Product not found with id: " + id);
		}
	}
}
