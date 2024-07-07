package com.ecommerce.ecommerce.infra.HandlerErros;

public class NotFoundCustomException extends RuntimeException {
	public NotFoundCustomException(String msg) {
		super(msg);
	}
}