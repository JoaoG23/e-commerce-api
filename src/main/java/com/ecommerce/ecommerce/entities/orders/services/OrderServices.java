package com.ecommerce.ecommerce.entities.orders.services;

import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.repository.OrderItemsRepository;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Transactional
	public Order create(OrderInsertedDTO orderDTO) {
		var order = new Order();
		BeanUtils.copyProperties(orderDTO, order);

		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new NotFoundCustomException("User not found"));

		if (user.getRole() != UserRole.COSTUMER) throw new NotFoundCustomException("Costumer don't exist");

		Order orderCreated = orderRepository.save(order);

		// add items
		// add product in the items
		// return sum value product + quatity product in API
		order.setUser(user);

		List<OrderItemInsertedDTO> itemsDto = orderDTO.getItems();

		itemsDto.forEach(itemDto -> {
			var itemsModel = new OrderItem();

			BeanUtils.copyProperties(itemDto, itemsModel);
			itemsModel.setProduct(productRepository.findById(itemDto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));
			itemsModel.setOrder(orderCreated);
			orderItemsRepository.save(itemsModel);
		});

		return orderRepository.save(order);
	}

	@Transactional
	public void deleteById(String id) {
		validateOrderNotExists(id);
		orderRepository.deleteById(id);

	}

	@Transactional
	public Order updateById(String id, OrderInsertedDTO orderDTO) {
		validateOrderNotExists(id);
		var order = new Order();
		BeanUtils.copyProperties(orderDTO, order);

		order.setId(id);
		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new NotFoundCustomException("Costumer not found"));

		if (user.getRole() != UserRole.COSTUMER) throw new NotFoundCustomException("Costumer don't exist");

		order.setUser(user);

		return orderRepository.save(order);
	}

	public List<OrderDTO> findOrderItemsAll() {
		List<Object[]> orderModelList = (List<Object[]>) orderRepository.findAllOrderItems();
		List<OrderDTO> orderItemDTOs = orderModelList.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return orderItemDTOs;
	}

	public List<OrderDTO> findOrderItemsByOrderId(String orderId) {
		validateOrderNotExists(orderId);
		List<Object[]> orderModelList = (List<Object[]>) orderRepository.findOrderItemsByOrderId(orderId);
		List<OrderDTO> orderItemDTOs = orderModelList.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return orderItemDTOs;
	}

	private OrderDTO convertToOrderItemDTO(Object[] array) {
		return new OrderDTO(
				(String) array[0],   // orderId
				(String) array[1],   // methodPayment
				(BigDecimal) array[2], // totalPrice
				(LocalDateTime) array[3], // updatedAt
				(String) array[4],   // userId
				(String) array[5],   // id
				(Integer) array[6],  // quantity
				(String) array[7],   // productId
				(String) array[8],   // details
				(String) array[9],   // nameProduct
				(BigDecimal) array[10], // price
				(BigDecimal) array[11]  // totalPriceItem
		);
	}

	private void validateOrderNotExists(String id) {
		Optional<Order> orderFound = orderRepository.findById(id);
		if (orderFound.isEmpty()) {
			throw new NotFoundCustomException("Order not found with id: " + id);
		}
	}
}
