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
import org.springframework.stereotype.Service;

import java.util.Optional;

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
				imageProduct.getPath()
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
