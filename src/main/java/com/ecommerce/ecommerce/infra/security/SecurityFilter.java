package com.ecommerce.ecommerce.infra.security;

import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	@Autowired
	private TokenServices tokenServices;
	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String token = this.recoverToken(request);
		String login = tokenServices.validateToken(token);

		if (login != null) {
			User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));

			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // Busca autenticação e destribui na aplicação
			SecurityContextHolder.getContext().setAuthentication(authentication); // Inclui autenticação na aplicação
		}
		filterChain.doFilter(request, response);
	}

	public String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}
}
