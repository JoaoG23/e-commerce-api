package com.ecommerce.ecommerce.entities.users.authentication.controllers;

import com.ecommerce.ecommerce.entities.users.authentication.dtos.EmployeeRequestDTO;
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
	@DisplayName("Create one EMPLOYEE return success 204")
	void registerEmployee() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		var userCreated = new EmployeeRequestDTO(
				"Usuario de testes",
				"admin@teste.com",
				"admin"
		);
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/employee/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreated)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
	}

	@Test
	@DisplayName("Create one COSTUMER return success 204")
	void registerCostumer() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		var userCreated = new EmployeeRequestDTO(
				"Costumer",
				"costumer@costumer.com",
				"costumer"
		);
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/costumer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreated)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
	}
}