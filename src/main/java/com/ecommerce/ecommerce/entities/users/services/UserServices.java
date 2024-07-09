package com.ecommerce.ecommerce.entities.users.services;


import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String register(User user) {
		var encoderPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encoderPassword);

		userRepository.save(user);
		return "User saved with success";
	}

//	@Transactional
//	public String updateById(String id, User userModel) {
//		validateIfUserNotExistsById(id);
//
//		var encoderPassword = new BCryptPasswordEncoder().encode(userModel.getPassword());
//		userModel.setPassword(encoderPassword);
//		userModel.setId(id);
//
//		this.userRepository.save(userModel);
//		return "User updated with success";
//	}
//
//	public List<UserViewedDTO> findAll() {
//
//		List<User> users = userRepository.findAll();
//		List<UserViewedDTO> userDTOs = new ArrayList<>();
//
//		for (User user : users) {
//			UserViewedDTO userViewedDTO = convertModelToUserViewedDTO(user);
//			userDTOs.add(userViewedDTO);
//		}
//		return userDTOs;
//	}
//
//	public Page<UserViewedDTO> findAllByPage(Pageable pageable) {
//		Page<User> userPage = userRepository.findAll(pageable);
//
//		List<UserViewedDTO> userDTOs = userPage.getContent().stream()
//				.map(this::convertModelToUserViewedDTO)
//				.collect(Collectors.toList());
//
//		return new PageImpl<>(userDTOs, pageable, userPage.getTotalElements());
//	}
//
//	public UserViewedDTO findById(String id) {
//		validateIfUserNotExistsById(id);
//
//		User userModel = this.userRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("User not found with id: " + id));
//		return convertModelToUserViewedDTO(userModel);
//	}
//
//	public String deleteById(String id) {
//		validateIfUserNotExistsById(id);
//
//		userRepository.deleteById(id);
//		return "User deleted with success" + id;
//	}
//
//
//	private UserViewedDTO convertModelToUserViewedDTO(User userModel) {
//		// Create a new UserDTO object and populate it with data from User
//		UserViewedDTO userDTO = new UserViewedDTO();
//		userDTO.setId(userModel.getId());
//		userDTO.setUsername(userModel.getUsername());
//		userDTO.setFullname(userModel.getFullname());
//
//		return userDTO;
//	}
//
//	private void validateIfUserNotExistsById(String id) {
//		Optional<User> userFound = userRepository.findById(id);
//		if (userFound.isEmpty()) {
//			throw new NotFoundCustomException("User not found with id: " + id);
//		}
//	}
}
