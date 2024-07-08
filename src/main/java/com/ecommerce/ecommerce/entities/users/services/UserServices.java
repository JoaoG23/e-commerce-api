package com.ecommerce.ecommerce.entities.users.services;


import com.ecommerce.ecommerce.entities.users.model.UserModel;
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
	public String register(UserModel userModel) {
		var encoderPassword = new BCryptPasswordEncoder().encode(userModel.getPassword());
		userModel.setPassword(encoderPassword);

		userRepository.save(userModel);
		return "User saved with success";
	}

//	@Transactional
//	public String updateById(String id, UserModel userModel) {
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
//		List<UserModel> users = userRepository.findAll();
//		List<UserViewedDTO> userDTOs = new ArrayList<>();
//
//		for (UserModel user : users) {
//			UserViewedDTO userViewedDTO = convertModelToUserViewedDTO(user);
//			userDTOs.add(userViewedDTO);
//		}
//		return userDTOs;
//	}
//
//	public Page<UserViewedDTO> findAllByPage(Pageable pageable) {
//		Page<UserModel> userPage = userRepository.findAll(pageable);
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
//		UserModel userModel = this.userRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("User not found with id: " + id));
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
//	private UserViewedDTO convertModelToUserViewedDTO(UserModel userModel) {
//		// Create a new UserDTO object and populate it with data from UserModel
//		UserViewedDTO userDTO = new UserViewedDTO();
//		userDTO.setId(userModel.getId());
//		userDTO.setUsername(userModel.getUsername());
//		userDTO.setFullname(userModel.getFullname());
//
//		return userDTO;
//	}
//
//	private void validateIfUserNotExistsById(String id) {
//		Optional<UserModel> userFound = userRepository.findById(id);
//		if (userFound.isEmpty()) {
//			throw new NotFoundCustomException("User not found with id: " + id);
//		}
//	}
}
