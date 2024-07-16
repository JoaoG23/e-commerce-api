package com.ecommerce.ecommerce.entities.stock.controllers;

import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedDTO;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
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

		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
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
//
//	@Test
//	@DisplayName("Create one stock with images success and return 204")
//	void createOneCase2() throws Exception {
//		this.createUserInitial();
//
//		List<ImageProductCreatedDTO> images = new ArrayList<ImageProductCreatedDTO>();
//		images.add(new ImageProductCreatedDTO(null, "image1.png", null));
//		images.add(new ImageProductCreatedDTO(null, "image2.png", null));
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		StockCreatedDTO stock = new StockCreatedDTO(
//				null,
//				"Stock",
//				new BigDecimal(10.30),
//				"Details of stock",
//				images
//		);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
//						.content(objectMapper.writeValueAsString(stock)))
//				.andExpect(MockMvcResultMatchers.status().isCreated())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Stock"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(new BigDecimal(10.30)))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of stock"));
//	}
//
//	@Test
//	@DisplayName("Update a stock successfully and return 200")
//	void updateOneCase1() throws Exception {
//		this.createUserInitial();
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		String stockString = this.createStock();
//
//		// Transform in JSON object
//		JSONObject stock = new JSONObject(stockString);
//		// Access the 'id' property
//		String id = stock.getString("id");
//
//		var stockUpdated = new StockCreatedDTO(
//				null,
//				"Edited Stock",
//				new BigDecimal(10.30),
//				"Details of stock",
//				new ArrayList<ImageProductCreatedDTO>()
//		);
//
//		mockMvc.perform(MockMvcRequestBuilders.put("/stocks/{id}", id)
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
//						.content(objectMapper.writeValueAsString(stockUpdated)))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Edited Stock"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(new BigDecimal(10.30)))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of stock"));
//	}
//

//
//	@Test
//	@DisplayName("Find all stocks and return success 200")
//	void findAllCase1() throws Exception {
//		this.createUserInitial();
//		this.createStock();
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Stock"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].details").value("Details of stock"));
//	}
//
//	@Test
//	@DisplayName("Find all by page and return success 200")
//	void findByPageCase2() throws Exception {
//		this.createUserInitial();
//		this.createStock();
//		this.createStock();
//		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/page")
//						.param("page", "0")
//						.param("size", "1")
//						.param("sort", "id")
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
//	}
//
//	@Test
//	@DisplayName("Find one by id and return success 200")
//	void findOneByIdCase1() throws Exception {
//		// Create Stock
//		this.createUserInitial();
//		String stockString = this.createStock();
//		JSONObject stock = new JSONObject(stockString);
//		// Extract Id
//		String id = stock.getString("id");
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{id}", id)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Stock"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Details of stock"));
//	}

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
