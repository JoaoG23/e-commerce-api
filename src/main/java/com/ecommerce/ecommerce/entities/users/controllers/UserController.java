package com.ecommerce.ecommerce.entities.users.controllers;

import com.ecommerce.ecommerce.entities.users.dtos.UserDTO;
import com.ecommerce.ecommerce.entities.users.dtos.UserViewedDTO;
import com.ecommerce.ecommerce.entities.users.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserServices userServices;


	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateById(@PathVariable(value = "id") String id, @RequestBody @Valid UserDTO userDTO) {
		var response = userServices.updateById(id, userDTO);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") String id) {
		userServices.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<UserViewedDTO>> findAll() {
		var users = userServices.findAll();
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserViewedDTO> findOneById(@PathVariable(value = "id") String id) {
		var user = userServices.findById(id);
		return (ResponseEntity<UserViewedDTO>) ResponseEntity.ok().body(user);
	}

	@GetMapping("page")
	public ResponseEntity<Page<UserViewedDTO>> getAllUsers(@PageableDefault(
			page = 0,
			size = 5,
			sort = "id",
			direction = Sort.Direction.ASC) Pageable pageable) {
		var pagesOfUsers = userServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(pagesOfUsers);
	}
}
