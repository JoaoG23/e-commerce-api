package com.ecommerce.ecommerce.entities.productsimagens.services;

import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductViewedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProductModel;
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

	@Transactional
	public ImageProductModel create(ImageProductCreatedDTO imageProductDTO) {

		ImageProductModel imageProductModel = new ImageProductModel();
		BeanUtils.copyProperties(imageProductDTO, imageProductModel);
		return imageProductRepository.save(imageProductModel);
	}

	@Transactional
	public void updateById(String id, ImageProductCreatedDTO imageProductDTO) {

		validateIfImageProductNotExistsById(id);

		ImageProductModel imageProductModel = new ImageProductModel();
		imageProductModel.setId(id);
		imageProductRepository.save(imageProductModel);
	}

	public List<ImageProductViewedDTO> findAll() {

		List<ImageProductModel> imageProducts = imageProductRepository.findAll();
		List<ImageProductViewedDTO> imageProductDTOs = new ArrayList<>();

		for (ImageProductModel imageProduct : imageProducts) {
			ImageProductViewedDTO imageProductDTO = convertModelToImageProductViewedDTO(imageProduct);
			imageProductDTOs.add(imageProductDTO);
		}
		return imageProductDTOs;
	}

	public Page<ImageProductViewedDTO> findAllByPage(Pageable pageable) {
		Page<ImageProductModel> pages = imageProductRepository.findAll(pageable);

		List<ImageProductViewedDTO> imageProductDTOs = pages.getContent().stream().map(this::convertModelToImageProductViewedDTO).collect(Collectors.toList());

		return new PageImpl<>(imageProductDTOs, pageable, pages.getTotalElements());
	}

	public ImageProductViewedDTO findById(String id) {
		validateIfImageProductNotExistsById(id);

		Optional<ImageProductModel> imageProductModelOptional = imageProductRepository.findById(id);
		ImageProductModel imageProductModel = imageProductModelOptional.get();
		return convertModelToImageProductViewedDTO(imageProductModel);
	}

	public void deleteById(String id) {
		validateIfImageProductNotExistsById(id);
		imageProductRepository.deleteById(id);
	}

	private ImageProductViewedDTO convertModelToImageProductViewedDTO(ImageProductModel imageProductModel) {
		ImageProductViewedDTO imageProductDTO = new ImageProductViewedDTO(
				imageProductModel.getId(),
				imageProductModel.getPath(),
				imageProductModel.getProduct()
		);

		return imageProductDTO;
	}

	private void validateIfImageProductNotExistsById(String id) {
		Optional<ImageProductModel> imageProductFound = imageProductRepository.findById(id);
		if (imageProductFound.isEmpty()) {
			throw new NotFoundCustomException("ImageProduct not found with id: " + id);
		}
	}
}
