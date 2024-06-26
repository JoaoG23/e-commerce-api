package com.ecommerce.ecommerce.entities.users.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserViewedDTO {

	private Long id;
	private String username;
	private String fullname;
}
