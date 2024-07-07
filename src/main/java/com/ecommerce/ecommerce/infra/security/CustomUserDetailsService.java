package com.ecommerce.ecommerce.infra.security;

import com.ecommerce.ecommerce.entities.users.model.UserModel;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}