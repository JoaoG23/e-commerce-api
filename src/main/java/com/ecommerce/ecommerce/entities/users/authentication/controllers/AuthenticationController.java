package com.ecommerce.ecommerce.entities.users.authentication.controllers;

import com.ecommerce.ecommerce.entities.users.authentication.dtos.AuthResponseDTO;
import com.ecommerce.ecommerce.entities.users.authentication.dtos.CostumerRequestDTO;
import com.ecommerce.ecommerce.entities.users.authentication.dtos.EmployeeRequestDTO;
import com.ecommerce.ecommerce.entities.users.authentication.dtos.LoginRequestDTO;
import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import com.ecommerce.ecommerce.infra.HandlerErros.UserNotFoundException;
import com.ecommerce.ecommerce.infra.security.TokenServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenServices tokenServices;


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO body) throws NotFoundCustomException {
		try {
			User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new UserNotFoundException("User or password invalid"));
			String userId = user.getId();
			if (passwordEncoder.matches(body.password(), user.getPassword())) {
				String token = tokenServices.generateToken(user);
				return ResponseEntity.ok(new AuthResponseDTO(userId, token));
			}
			return ResponseEntity.badRequest().build();
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("User or password invalid");
		}
	}

	@PostMapping("/employee/register")
	public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRequestDTO body) {
		Optional<User> user = this.repository.findByEmail(body.email());

		if (user.isEmpty()) {
			User newUser = new User();
			newUser.setPassword(passwordEncoder.encode(body.password()));
			newUser.setEmail(body.email());
			newUser.setName(body.name());
			newUser.setRole(UserRole.EMPLOYEE);

			User savedUser = this.repository.save(newUser);
			String userId = savedUser.getId();

			String token = this.tokenServices.generateToken(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(userId, token));
		}
		return ResponseEntity.badRequest().body("Employee already exists");
	}

	@PostMapping("/costumer/register")
	public ResponseEntity<?> registerCostumer(@RequestBody @Valid CostumerRequestDTO body) {
		Optional<User> user = this.repository.findByEmail(body.email());

		if (user.isEmpty()) {
			User newUser = new User();
			newUser.setPassword(passwordEncoder.encode(body.password()));
			newUser.setEmail(body.email());
			newUser.setName(body.name());
			newUser.setRole(UserRole.COSTUMER);

			User savedUser = this.repository.save(newUser);
			String userId = savedUser.getId();

			String token = this.tokenServices.generateToken(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(userId, token));
		}
		return ResponseEntity.badRequest().body("Costumer already exists");
	}
}
