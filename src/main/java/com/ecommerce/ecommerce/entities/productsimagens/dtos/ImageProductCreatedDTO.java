package com.ecommerce.ecommerce.entities.productsimagens.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageProductCreatedDTO {
	private String id;
	@NotBlank(message = "Path is mandatory")
	private String path;
	private String productsId;
}
