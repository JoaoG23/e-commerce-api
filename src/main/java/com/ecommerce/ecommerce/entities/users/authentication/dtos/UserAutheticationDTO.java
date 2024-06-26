package com.ecommerce.ecommerce.entities.users.authentication.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAutheticationDTO {
	private String username;
	private String password;
}
