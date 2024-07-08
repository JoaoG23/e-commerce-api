package com.ecommerce.ecommerce.entities.users.authentication.controllers;

import com.ecommerce.ecommerce.entities.users.authentication.dtos.LoginRequestDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;


	@BeforeEach
	@AfterEach
	void deleteAll() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("Should to do login and return status 2XX")
	void toDologin() throws Exception {
		//Arrange
		ObjectMapper objectMapper = new ObjectMapper();

		createUser();

		UserModel userModel = new UserModel();
		userModel.setUsername("joao");
		userModel.setPassword("joao");
		userModel.setFullname("Joao Guilherme");

		userRepository.save(userModel);

		System.out.println(userRepository.findAll());
		LoginRequestDTO userDTO = new LoginRequestDTO();
		userDTO.setUsername("joao");
		userDTO.setPassword("joao");
		// Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(MockMvcResultMatchers.status().isOk());
//				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.token"));
	}

	private void createUser() {
//		UserModel userModel = new UserModel();
//		userModel.setUsername("joao");
//		userModel.setPassword("joao");
//		userModel.setFullname("Joao Guilherme");
//
//		userRepository.save(userModel);
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		UserModel userModel = new UserModel();
//		userModel.setUsername("joao");
//		userModel.setPassword("joao");
//		userModel.setFullname("Joao Guilherme");
//
//
//		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//						.content(objectMapper.writeValueAsString(userModel)))
//						.andReturn();
//
//		String response = mvcResult
//				.getResponse()
//				.getContentAsString();
//		return response;
	}

}