package com.ecommerce.ecommerce.infra.HandlerErros;


public class CustomException extends RuntimeException {
	public CustomException(String msg) {
		super(msg);
	}
}