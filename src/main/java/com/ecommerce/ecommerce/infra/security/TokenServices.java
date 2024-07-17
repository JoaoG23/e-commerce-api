package com.ecommerce.ecommerce.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ecommerce.ecommerce.entities.users.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServices {
	@Value("${api.security.token.secret}")
	private String secret;
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("ecommerce_api")
					.withSubject(user.getEmail())
					.withExpiresAt(this.getDateExpiry())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while authentication");
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("ecommerce_api")
					.build()
					.verify(token)
					.getSubject();

		} catch (JWTVerificationException exception){
			/*
			Token será verificado no interceptor filterChair
			se viér como (null), ficará como usuário
			não autenticado;
			 */
			return null;
		}
	}

	private Instant getDateExpiry() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
