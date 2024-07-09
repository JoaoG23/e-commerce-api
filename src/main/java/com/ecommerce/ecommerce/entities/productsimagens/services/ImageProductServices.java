package com.ecommerce.ecommerce.entities.productsimagens.services;

import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductViewedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;
import com.ecommerce.ecommerce.entities.productsimagens.repository.ImageProductRepository;
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
public class ImageProductServices {
	@Autowired
	private ImageProductRepository imageProductRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public ImageProduct create(ImageProductCreatedDTO imageProductDTO) {

		ImageProduct imageProduct = new ImageProduct();
		BeanUtils.copyProperties(imageProductDTO, imageProduct);

		this.validateIfExistProductAndInsert(imageProductDTO, imageProduct);
		return imageProductRepository.save(imageProduct);
	}

	@Transactional
	public void updateById(String id, ImageProductCreatedDTO imageProductDTO) {

		validateIfImageProductNotExistsById(id);

		ImageProduct imageProduct = new ImageProduct();
		imageProduct.setId(id);
		imageProductRepository.save(imageProduct);
	}

	public List<ImageProductViewedDTO> findAll() {

		List<ImageProduct> imageProducts = imageProductRepository.findAll();
		List<ImageProductViewedDTO> imageProductDTOs = new ArrayList<>();

		for (ImageProduct imageProduct : imageProducts) {
			ImageProductViewedDTO imageProductDTO = convertModelToImageProductViewedDTO(imageProduct);
			imageProductDTOs.add(imageProductDTO);
		}
		return imageProductDTOs;
	}

	public Page<ImageProductViewedDTO> findAllByPage(Pageable pageable) {
		Page<ImageProduct> pages = imageProductRepository.findAll(pageable);

		List<ImageProductViewedDTO> imageProductDTOs = pages.getContent().stream().map(this::convertModelToImageProductViewedDTO).collect(Collectors.toList());

		return new PageImpl<>(imageProductDTOs, pageable, pages.getTotalElements());
	}

	public ImageProductViewedDTO findById(String id) {
		validateIfImageProductNotExistsById(id);

		Optional<ImageProduct> imageProductModelOptional = imageProductRepository.findById(id);
		ImageProduct imageProduct = imageProductModelOptional.get();
		return convertModelToImageProductViewedDTO(imageProduct);
	}

	public void deleteById(String id) {
		validateIfImageProductNotExistsById(id);
		imageProductRepository.deleteById(id);
	}

	private ImageProductViewedDTO convertModelToImageProductViewedDTO(ImageProduct imageProduct) {
		ImageProductViewedDTO imageProductDTO = new ImageProductViewedDTO(
				imageProduct.getId(),
				imageProduct.getPath(),
				imageProduct.getProduct()
		);

		return imageProductDTO;
	}

	private void validateIfImageProductNotExistsById(String id) {
		Optional<ImageProduct> imageProductFound = imageProductRepository.findById(id);
		if (imageProductFound.isEmpty()) {
			throw new NotFoundCustomException("ImageProduct not found with id: " + id);
		}
	}

	private ImageProduct validateIfExistProductAndInsert(ImageProductCreatedDTO dto, ImageProduct model) {
		if (dto.getProductsId() != null) {
			var product = productRepository.findById(dto.getProductsId())
					.orElseThrow(() -> new NotFoundCustomException("Product not found"));
			model.setProduct(product);
		}
		return model;
	}
}
