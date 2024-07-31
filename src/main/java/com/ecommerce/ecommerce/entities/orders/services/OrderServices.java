package com.ecommerce.ecommerce.entities.orders.services;

import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemQueryDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.repository.OrderItemsRepository;
import com.ecommerce.ecommerce.entities.orderitems.services.OrderItemServices;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.enums.OrderState;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.stock.dtos.ItemStockIncreaseDTO;
import com.ecommerce.ecommerce.entities.stock.model.Stock;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.entities.stock.services.StockService;
import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private OrderItemServices orderItemServices;

	@Autowired
	private StockService stockService;

	@Transactional
	public Order create(OrderInsertedDTO orderDTO) {
		var order = new Order();
		BeanUtils.copyProperties(orderDTO, order);
		order.setOrderState(OrderState.OPEN);

		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new NotFoundCustomException("User not found"));

		if (user.getRole() != UserRole.COSTUMER) throw new NotFoundCustomException("Costumer don't exist");

		Order orderCreated = orderRepository.save(order);
		order.setUser(user);

		List<ItemInsertedDTO> itemsDto = orderDTO.getItems();

		itemsDto.forEach(itemDto -> {
			var itemEntity = new OrderItem();
			String productId = itemDto.getProductId();
			Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundCustomException("Product not found"));

			BeanUtils.copyProperties(itemDto, itemEntity);
			itemEntity.setProduct(product);
			itemEntity.setOrder(orderCreated);

			String orderId = orderCreated.getId();

			Boolean isAlreadyExistsItem = orderItemsRepository.existsByOrderAndProduct(
					orderRepository.findById(orderId).orElseThrow(() -> new NotFoundCustomException("Order not found")),
					productRepository.findById(productId).orElseThrow(() -> new NotFoundCustomException("Product not found"))
			);
			if (isAlreadyExistsItem == true) throw new NotFoundCustomException("Product already exists in this order");

			checkStockQuantity(itemDto);
			orderItemsRepository.save(itemEntity);
		});
		return orderCreated;
	}

	@Transactional
	public void deleteById(String id) {
		validateOrderNotExists(id);
		orderRepository.deleteById(id);

	}

	@Transactional
	public Order updateById(String id, OrderInsertedDTO orderDTO) {
		validateOrderNotExists(id);
		Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("Order not found"));
		BeanUtils.copyProperties(orderDTO, order);

		order.setId(id);
		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new NotFoundCustomException("Costumer not found"));

		if (user.getRole() != UserRole.COSTUMER) throw new NotFoundCustomException("Costumer don't exist");

		order.setUser(user);

		return orderRepository.save(order);
	}

	@Transactional
	public Order closeOrderById(String id) {
		validateOrderNotExists(id);
		Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("Order not found"));
		order.setOrderState(OrderState.BEGIN_PICKED);

		List<ItemQueryDTO> items = orderItemServices.findAllByOrderId(id);

		// get order Id;
		items.forEach(item -> {
			ItemStockIncreaseDTO itemStock = new ItemStockIncreaseDTO(false,
					item.getQuantity(),
					item.getProductId()
			);
			stockService.decreaseQuantityProduct(itemStock);
		});
		return orderRepository.save(order);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Optional<Order> findById(String id) {
		return orderRepository.findById(id);
	}

	private void validateOrderNotExists(String id) {
		Optional<Order> orderFound = orderRepository.findById(id);
		if (orderFound.isEmpty()) {
			throw new NotFoundCustomException("Order not found with id: " + id);
		}
	}


	private void checkStockQuantity(ItemInsertedDTO itemDto) {
		Product product = productRepository.findById(itemDto.getProductId())
				.orElseThrow(() -> new NotFoundCustomException("Product not found"));
		Stock productStock = stockRepository.findByProduct(product);
		Integer availableQuantity = productStock.getQuantity();

		int remainingQuantity = availableQuantity - itemDto.getQuantity();
		if (remainingQuantity < 0)
			throw new NotFoundCustomException("Insufficient stock! Requested quantity: " + itemDto.getQuantity() + ". Available quantity: " + availableQuantity);
	}

	private void checkOrderIsOpenById(String id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("Order not found"));
		if(order.getOrderState() != OrderState.OPEN) throw new NotFoundCustomException("Order is BEGIN PICKED or CLOSED. You can't update or manager the order");
	}


	private void checkUserIsCostumerById(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("User not found"));
		if(user.getRole() != UserRole.COSTUMER) throw new NotFoundCustomException("Costumer don't exist");
	}
}
