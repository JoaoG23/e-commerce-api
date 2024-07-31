package com.ecommerce.ecommerce.entities.orders.controllers;

import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.orders.services.OrderServices;
import com.ecommerce.ecommerce.entities.products.dtos.ProductCreatedWithStockDTO;
import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.products.services.ProductServices;
import com.ecommerce.ecommerce.entities.productsimagens.dtos.ImageProductCreatedDTO;
import com.ecommerce.ecommerce.entities.productsimagens.repository.ImageProductRepository;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockCreatedDTO;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.entities.users.authentication.dtos.CostumerRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {
	@Value("${tokentestes}")
	private String TOKEN;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImageProductRepository imagesRepository;
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductServices productServices;

	@Autowired
	private OrderServices orderServices;

	@BeforeEach
	@AfterEach
	void deleteAll() throws Exception {
		stockRepository.deleteAll();
		imagesRepository.deleteAll();
		productRepository.deleteAll();
		userRepository.deleteAll();
		orderRepository.deleteAll();
	}

	/*
	[X] Create order with items for costumer
	[x] Close order with items
	[] Try to update order closed or begin picked
	[] Try to delete order closed or begin picked
	[x] Try to get all orders
	* */
	@Test
	@DisplayName("Create order with items for costumer")
	void createOrderCase1() throws Exception {
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var objectMapper = new ObjectMapper();
		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		mockMvc.perform(MockMvcRequestBuilders.post("/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(order)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.methodPayment").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderState").value("OPEN"));
	}

	@Test
	@DisplayName("Should close the order change state to BEGIN_PICKED")
	void closeOrderCase1() throws Exception {
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		Order orderCreated = orderServices.create(order);
		String orderId = orderCreated.getId();

		mockMvc.perform(MockMvcRequestBuilders.patch("/orders/close/{orderId}", orderId)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.methodPayment").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderState").value("BEGIN_PICKED"));
	}

	@Test
	@DisplayName("Should to update the order by id with state OPEN")
	void updateOrderCase1() throws Exception {
		var objectMapper = new ObjectMapper();
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		Order orderCreated = orderServices.create(order);
		String orderId = orderCreated.getId();

		// Order updated
		OrderInsertedDTO orderUpdated = new OrderInsertedDTO();
		orderUpdated.setUserId(userId);
		orderUpdated.setMethodPayment("CREDIT");

		mockMvc.perform(MockMvcRequestBuilders.put("/orders/{orderId}", orderId)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
						.content(objectMapper.writeValueAsString(orderUpdated)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.methodPayment").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderState").value("OPEN"));
	}

	@Test
	@DisplayName("Should to delete the order by id with state OPEN")
	void deleteOrderCase1() throws Exception {
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		Order orderCreated = orderServices.create(order);
		String orderId = orderCreated.getId();

		mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{orderId}", orderId)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("Should to get order by id")
	void getOneOrderByIdCase1() throws Exception {
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		Order orderCreated = orderServices.create(order);
		String orderId = orderCreated.getId();

		mockMvc.perform(MockMvcRequestBuilders.get("/orders/{orderId}", orderId)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.methodPayment").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderState").value("OPEN"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems").isArray());
	}

	@Test
	@DisplayName("Should to get all orders")
	void getAllOrderByIdCase1() throws Exception {
		this.createEmployee();
		// Create user
		String costumerString = this.createCostumer();
		var costumer = new JSONObject(costumerString);

		String userId = costumer.getString("userId");

		// Create product with stock
		Product product = createProduct();
		assertNotNull(product.getId());

		// Test create order with items
		var item = new ItemInsertedDTO();
		item.setProductId(product.getId());
		item.setQuantity(3);

		assertNotNull(item.getProductId());

		List<ItemInsertedDTO> items = new ArrayList<ItemInsertedDTO>();
		items.add(item);

		var order = new OrderInsertedDTO();
		order.setUserId(userId);
		order.setMethodPayment("AVISTA");
		order.setItems(items);

		Order orderCreated = orderServices.create(order);
		String orderId = orderCreated.getId();

		mockMvc.perform(MockMvcRequestBuilders.get("/orders")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
	}

	private String createEmployee() throws Exception {
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

	private String createCostumer() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var userCreated = new CostumerRequestDTO(
				"Costumer",
				"costumer@costumer.com",
				"costumer"
		);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/auth/costumer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreated)))
				.andReturn();

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}

	private Product createProduct() {
		Product product = this.productServices.createWithStock(new ProductCreatedWithStockDTO(null,
				"Product",
				new BigDecimal(10.30),
				"Details of product",
				new ItemStockCreatedDTO(null, null, 3, 88.00),
				new ArrayList<ImageProductCreatedDTO>()
		));
		return product;
	}
}
