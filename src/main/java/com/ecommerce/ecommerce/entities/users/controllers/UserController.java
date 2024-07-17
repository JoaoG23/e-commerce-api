package com.ecommerce.ecommerce.entities.users.controllers;

import com.ecommerce.ecommerce.entities.users.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


	@Autowired
	private UserServices userServices;


//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateById(@PathVariable(value = "id") Long id, @RequestBody @Valid UserCreatedDTO userDTO) {
//
//		User userModel = new User();
//		BeanUtils.copyProperties(userDTO, userModel);
//
//		String response = userServices.updateById(id, userModel);
//		return ResponseEntity.ok().body(response);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id) {
//
//		userServices.deleteById(id);
//		return ResponseEntity.noContent().build();
//	}
//
//	@GetMapping
//	public ResponseEntity<List<UserViewedDTO>> findAll() {
//		var users = userServices.findAll();
//		return ResponseEntity.ok().body(users);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<UserViewedDTO> findOneById(@PathVariable(value = "id") Long id) {
//		var user = userServices.findById(id);
//		return (ResponseEntity<UserViewedDTO>) ResponseEntity.ok().body(user);
//	}
//
//	@GetMapping("page")
//	public ResponseEntity<Page<UserViewedDTO>> getAllUsers(@PageableDefault(
//			page = 0,
//			size = 5,
//			sort = "id",
//			direction = Sort.Direction.ASC) Pageable pageable) {
//		var pagesOfUsers = userServices.findAllByPage(pageable);
//		return ResponseEntity.ok().body(pagesOfUsers);
//	}
}
