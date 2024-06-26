package com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;

public class NotFoundCustomException extends RuntimeException {
	public NotFoundCustomException(String msg) {
		super(msg);
	}
}