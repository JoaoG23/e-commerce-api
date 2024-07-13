package com.ecommerce.ecommerce.entities.products.controllers;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
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
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
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
		productRepository.deleteAll();
		imagesRepository.deleteAll();
		userRepository.deleteAll();
	}


	@Test
	@DisplayName("Create one product with success and return 204")
	void createOneCase1() throws Exception {

		this.createUserInitial();

		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
		ObjectMapper objectMapper = new ObjectMapper();
		ProductCreatedDTO product = new ProductCreatedDTO(
				null,
				"Product",
				new BigDecimal(10.30),
				"Details of product",
				images
		);

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of product"));
	}

	@Test
	@DisplayName("Create one product with images success and return 204")
	void createOneCase2() throws Exception {

		this.createUserInitial();

		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
		images.add(new ImageProductCreatedDTO(null, "image1.png", null));
		images.add(new ImageProductCreatedDTO(null, "image2.png", null));

		ObjectMapper objectMapper = new ObjectMapper();
		ProductCreatedDTO product = new ProductCreatedDTO(
				null,
				"Product",
				new BigDecimal(10.30),
				"Details of product",
				images
		);

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(new BigDecimal(10.30)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of product"));
	}

	@Test
	@DisplayName("Update a product successfully and return 200")
	void updateOneCase1() throws Exception {

		this.createUserInitial();

		ObjectMapper objectMapper = new ObjectMapper();
		String productString = this.createProduct();

		// Transform in JSON object
		JSONObject product = new JSONObject(productString);
		// Access the 'id' property
		String id = product.getString("id");


		var productUpdated = new ProductCreatedDTO(
				null,
				"Edited Product",
				new BigDecimal(10.30),
				"Details of product",
				new ArrayList<ImageProductCreatedDTO>()
		);

		mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(productUpdated)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Edited Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(new BigDecimal(10.30)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of product"));
	}

	@Test
	@DisplayName("Delete one product with success and return 200")
	void deleteOneById() throws Exception {
		this.createUserInitial();

		String productString = this.createProduct();

		var product = new JSONObject(productString);

		String id = product.getString("id");

		mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted: " + id));
	}

	@Test
	@DisplayName("Find all products and return success 200")
	void findAllCase1() throws Exception {
		this.createUserInitial();
		this.createProduct();

		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].details").value("Details of product"));
	}

	@Test
	@DisplayName("Find all by page and return success 200")
	void findByPageCase2() throws Exception {
		this.createUserInitial();
		this.createProduct();
		this.createProduct();
		mockMvc.perform(MockMvcRequestBuilders.get("/products/page")
						.param("page", "0")
						.param("size", "1")
						.param("sort", "id")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
	}

	@Test
	@DisplayName("Find one by id and return success 200")
	void findOneByIdCase1() throws Exception {
		// Create Product
		this.createUserInitial();
		String productString = this.createProduct();
		JSONObject product = new JSONObject(productString);
		// Extract Id
		String id = product.getString("id");


		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", id)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of product"));
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

}
