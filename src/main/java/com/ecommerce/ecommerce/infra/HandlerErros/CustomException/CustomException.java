package com.ecommerce.ecommerce.infra.HandlerErros.CustomException;


public class CustomException extends RuntimeException {
	public CustomException(String msg) {
		super(msg);
	}
}