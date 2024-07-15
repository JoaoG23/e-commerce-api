package com.ecommerce.ecommerce.entities.productsimagens.controllers;


import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.model.ImageProduct;
import com.ecommerce.ecommerce.entities.productsimagens.repository.ImageProductRepository;
import com.ecommerce.ecommerce.entities.users.dtos.UserCreatedDTO;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ImageProductControllerTest {
	@Value("${tokentestes}")
	private String TOKEN;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImageProductRepository imagesRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	@AfterEach
	void deleteAll() throws Exception {
		imagesRepository.deleteAll();
		productRepository.deleteAll();
		userRepository.deleteAll();
	}


	@Test
	@DisplayName("Create picture for product with success and return 204")
	void createOneCase1() throws Exception {

		this.createUserInitial();

		String productString = this.createProduct();
		var product = new JSONObject(productString);
		String productsId = product.getString("id");

		var productToInsert = new ImageProductCreatedDTO(
				null,
				"C://",
				productsId
		);

		var objectMapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/products/image-products")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(productToInsert)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}


	@Test
	@DisplayName("Delete picture for product with success and return 204")
	void deleteOneCase1() throws Exception {

		this.createUserInitial();

		String productString = this.createProduct();
		var product = new JSONObject(productString);
		String productsId = product.getString("id");

		String imageId = this.createImageForProduct(productsId);


		mockMvc.perform(MockMvcRequestBuilders.delete("/products/image-products/{imageId}", imageId)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}



	@Test
	@DisplayName("Find one picture for product by id and return success 200")
	void findOneByIdCase1() throws Exception {
		this.createUserInitial();

		String productString = this.createProduct();
		var product = new JSONObject(productString);
		String productsId = product.getString("id");

		String imageId = this.createImageForProduct(productsId);


		mockMvc.perform(MockMvcRequestBuilders.get("/products/image-products/{id}", imageId)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.path").exists());
	}

	private String createUserInitial() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserCreatedDTO userCreated = new UserCreatedDTO(null,
				"Usuario de testes",
				"admin@teste.com",
				"admin"
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreated)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}

	private String createProduct() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		ProductCreatedDTO product = new ProductCreatedDTO(
				null,
				"Product",
				new BigDecimal(10.30),
				"Details of product",
				new ArrayList<ImageProductCreatedDTO>()
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/products")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}
	private String createImageForProduct(String productId) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var product = new ImageProductCreatedDTO(
				null,
				"Product",
				productId
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/products/image-products")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}
}