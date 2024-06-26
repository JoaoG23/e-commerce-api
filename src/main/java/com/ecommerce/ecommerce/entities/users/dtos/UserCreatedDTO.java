package com.ecommerce.ecommerce.entities.users.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class UserCreatedDTO {

	private Long id;

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String fullname;

}

