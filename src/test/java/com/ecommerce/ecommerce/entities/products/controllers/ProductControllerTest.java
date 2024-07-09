package com.ecommerce.ecommerce.entities.products.controllers;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.users.dtos.UserCreatedDTO;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private UserRepository userRepository;

	@BeforeEach
	@AfterEach
	void deleteAll() {
		productRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("Create one product with success and return 204")
	void createOneCase1() throws Exception {

		this.createUserInitial();

		ObjectMapper objectMapper = new ObjectMapper();
		ProductCreatedDTO product = new ProductCreatedDTO(
				null,
				"Product",
				new BigDecimal(10.30),
				"Descricão do produto",
				null
		);

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Descricão do produto"));
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
				"Descricão do produto",
				images
		);
		System.out.println(product);

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Descricão do produto")
//				.andExpect(MockMvcResultMatchers.jsonPath("$.imagesProduct[0].path").value("image1.png"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.imagesProduct[1].path").value("image2.png"));
	}
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
public String createUserInitial() throws Exception {
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
