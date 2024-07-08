package com.ecommerce.ecommerce.entities.products.controllers;
import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;


	@BeforeEach
	@AfterEach
	void deleteAll() {
		productRepository.deleteAll();
	}

//	@Test
//	@DisplayName("Create one product with success and return 204")
//	void createOneCase1() throws Exception {
//		// populate this with test data
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		var productDTO = productReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/products")
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // add JWT in Authorization header
//						.content(objectMapper.writeValueAsString(productDTO))) // send ProductCreatedDTO as JSON in the request body
//				.andExpect(MockMvcResultMatchers.status().isCreated())// expect CREATED status
//				.andExpect(MockMvcResultMatchers.jsonPath("$.nameProduct").value("Product"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição do produto"));
//	}
//
//	@Test
//	@DisplayName("Update a product successfully and return 200")
//	void updateOneCase1() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		String productString = createProduct();
//
//		// Transform in JSON object
//		JSONObject product = new JSONObject(productString);
//		// Access the 'id' property
//		Long id = Long.parseLong(product.getString("id"));
//		ProductCreatedDTO productDTO = productReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", id)
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//						.content(objectMapper.writeValueAsString(productDTO)))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.nameProduct").value("Product"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição do produto"));
//	}
//
//	@Test
//	@DisplayName("Delete one product with success and return 200")
//	void deleteOneById() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		// Create Product
//		String productString = createProduct();
//
//		JSONObject product = new JSONObject(productString);
//
//		// Extract Id
//		Long id = Long.parseLong(product.getString("id"));
//		ProductCreatedDTO productDTO = productReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", id)
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string("Product deleted " + id));
//
//	}
//
//	@Test
//	@DisplayName("Find all products and return success 200")
//	void findAllCase1() throws Exception {
//		createProduct();
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/products")
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nameProduct").value("Product"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Descrição do produto"));
//	}
//
//	@Test
//	@DisplayName("Find all by page and return success 200")
//	void findByPageCase2() throws Exception {
//		createProduct();
//		createProduct();
//		mockMvc.perform(MockMvcRequestBuilders.get("/products/page")
//						.param("page", "0")
//						.param("size", "1")
//						.param("sort", "id")
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
//	}
//
//	@Test
//	@DisplayName("Find one by id and return success 200")
//	void findOneByIdCase1() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		// Create Product
//		String productString = createProduct();
//
//		JSONObject product = new JSONObject(productString);
//
//		// Extract Id
//		Long id = Long.parseLong(product.getString("id"));
//		ProductCreatedDTO productDTO = productReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", id)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.nameProduct").value("Product"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição do produto"));
//	}
//
//	private String createProduct() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		ProductCreatedDTO productDTO = productReturned();
//
//		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//						.content(objectMapper.writeValueAsString(productDTO)))
//				.andReturn(); // get the MvcResult
//
//		String response = mvcResult
//				.getResponse()
//				.getContentAsString();
//		return response;
//	}
//
//
//	private ProductCreatedDTO productReturned() {
//		ProductCreatedDTO product = new ProductCreatedDTO();
//		product.setName("Product");
//		product.setPrice(BigDecimal.valueOf(102.0));
//		product.setDetails("Descrição do produto");
//		product.setTelephone("123456789");
//		return product;
//	}
}
