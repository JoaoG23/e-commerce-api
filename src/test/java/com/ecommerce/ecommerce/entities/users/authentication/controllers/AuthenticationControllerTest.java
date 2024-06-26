package com.ecommerce.ecommerce.entities.users.authentication.controllers;

import com.ecommerce.ecommerce.entities.users.authentication.dtos.UserAutheticationDTO;
import com.ecommerce.ecommerce.entities.users.dtos.UserDTO;
import com.ecommerce.ecommerce.entities.users.model.UserModel;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;


	@BeforeEach
	@AfterEach
	void deleteAll() {
		userRepository.deleteAll();
	}

	private void createUser() {
		UserModel userModel = new UserModel();
		userModel.setUsername("joao");
		userModel.setPassword("joao");
		userRepository.save(userModel);
	}

	@Test
	@DisplayName("Should to do of user and return status 2XX")
	void toDologin() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		UserAutheticationDTO userDTO = new UserAutheticationDTO();
		userDTO.setUsername("joao");
		userDTO.setPassword("joao");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.token"));
	}
}