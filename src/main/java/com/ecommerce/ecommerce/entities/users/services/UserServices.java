package com.ecommerce.ecommerce.entities.users.services;


import com.ecommerce.ecommerce.entities.users.dtos.UserDTO;
import com.ecommerce.ecommerce.entities.users.dtos.UserViewedDTO;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public UserDTO updateById(String id, UserDTO dto) {
		validateIfUserNotExistsById(id);
		var user = new User();
		var encoderPassword = new BCryptPasswordEncoder().encode(dto.password());
		user.setPassword(encoderPassword);
		BeanUtils.copyProperties(dto, user);
		this.userRepository.save(user);

		return new UserDTO(Optional.ofNullable(user.getId()), user.getName(), user.getEmail(), user.getPassword());
	}

	public List<UserViewedDTO> findAll() {

		List<User> users = userRepository.findAll();
		List<UserViewedDTO> userDTOs = new ArrayList<>();

		for (User user : users) {
			UserViewedDTO userViewedDTO = convertModelToUserViewedDTO(user);
			userDTOs.add(userViewedDTO);
		}
		return userDTOs;
	}

	public Page<UserViewedDTO> findAllByPage(Pageable pageable) {
		Page<User> userPage = userRepository.findAll(pageable);

		List<UserViewedDTO> userDTOs = userPage.getContent().stream()
				.map(this::convertModelToUserViewedDTO)
				.collect(Collectors.toList());

		return new PageImpl<>(userDTOs, pageable, userPage.getTotalElements());
	}
	public UserViewedDTO findById(String id) {
		validateIfUserNotExistsById(id);

		User userModel = this.userRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("User not found with id: " + id));
		return convertModelToUserViewedDTO(userModel);
	}
	public String deleteById(String id) {
		validateIfUserNotExistsById(id);

		userRepository.deleteById(id);
		return "User deleted with success" + id;
	}

	private UserViewedDTO convertModelToUserViewedDTO(User model) {
		var userDTO = new UserViewedDTO(model.getId(),model.getName(), model.getEmail());
		return userDTO;
	}

	private void validateIfUserNotExistsById(String id) {
		Optional<User> userFound = userRepository.findById(id);
		if (userFound.isEmpty()) {
			throw new NotFoundCustomException("User not found with id: " + id);
		}
	}
}
