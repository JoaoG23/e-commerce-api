package com.ecommerce.ecommerce.entities.stock.controllers;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockIncreaseDTO;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.entities.users.authentication.dtos.EmployeeRequestDTO;
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
class StockControllerTest {
	@Value("${tokentestes}")
	private String TOKEN;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	@AfterEach
	void deleteAll() throws Exception {
		productRepository.deleteAll();
		stockRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("Create one stock item with success and return 204")
	void createOneCase1() throws Exception {
		this.createUserInitial();

		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");

		ObjectMapper objectMapper = new ObjectMapper();
		var stock = new ItemStockCreatedDTO(
				null,
				productId,
				1,
				10.2
		);

		mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(stock)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(stock.quantity()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lotPrice").value(stock.lotPrice()));

	}

	@Test
	@DisplayName("Increase quantity stock item by productId with success and return 200")
	void increaseProductCase1() throws Exception {
		this.createUserInitial();

		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");

		ObjectMapper objectMapper = new ObjectMapper();

		String stockItem = this.createStockByProductId(productId);

		var increased = new ItemStockIncreaseDTO(
				true,
				1,
				productId
		);
		mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/increase-decrease")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(increased)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(increased.quantity()));
	}

	@Test
	@DisplayName("Decrease quantity stock item by productId with success and return 200")
	void decreaseProductCase1() throws Exception {
		this.createUserInitial();

		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");

		ObjectMapper objectMapper = new ObjectMapper();

		this.createStockByProductId(productId);

		var quantity = new ItemStockIncreaseDTO(
				false,
				1,
				productId
		);
		mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/increase-decrease")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(quantity)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(quantity.quantity()));
	}

	@Test
	@DisplayName("Error to decrease quantity less than 0 in stock item with success and return 200")
	void decreaseProductCase2() throws Exception {
		this.createUserInitial();

		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");

		ObjectMapper objectMapper = new ObjectMapper();

		this.createStockByProductId(productId);


		var quantity = new ItemStockIncreaseDTO(
				false,
				2,
				productId
		);
		mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/increase-decrease")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(quantity)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}


	@Test
	@DisplayName("Find all by page and return success 200")
	void findByPageCase1() throws Exception {
		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");
		this.createStockByProductId(productId);

		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/page")
						.param("page", "0")
						.param("size", "1")
						.param("sort", "id")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
	}

	@Test
	@DisplayName("Find one by productId and return success 200")
	void findOneByIdCase1() throws Exception {
		this.createUserInitial();

		String product = this.createProduct();
		var getProduct = new JSONObject(product);
		String productId = getProduct.getString("id");

		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
		ObjectMapper objectMapper = new ObjectMapper();
		var stock = new ItemStockCreatedDTO(
				null,
				productId,
				1,
				10.2
		);

		String stockItem = this.createStockByProductId(productId);

		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{productId}", productId)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId));
	}

	private String createUserInitial() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var userCreated = new EmployeeRequestDTO(
				"Usuario de testes",
				"admin@teste.com",
				"admin"
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/auth/employee/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreated)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}

	private String createProduct() throws Exception {

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

	private String createStockByProductId(String productId) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var stock = new ItemStockCreatedDTO(
				null,
				productId,
				1,
				12.22
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/stocks")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(stock)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}
}
