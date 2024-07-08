package com.ecommerce.ecommerce.infra.HandlerErros;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleStatus404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleStatus400(
			MethodArgumentNotValidException ex
	) {
		var erros = ex.getFieldErrors();
		var errosStream = erros.stream();// Pega lista de erros
		return ResponseEntity.badRequest().body(errosStream.map(Erros::new).toList());
	}

	public record Erros(String field, String message) {
		// Construtor que obtem o campo e mensagem
		public Erros(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
	// Excessão constumizada
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customException(CustomException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	// Excessão Not Found com mensa
	@ExceptionHandler(NotFoundCustomException.class)
	public ResponseEntity<?> handleStatus404Custom(NotFoundCustomException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
