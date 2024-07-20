package com.ecommerce.ecommerce.entities.orders.services;

import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderInsertedDTO;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.users.model.User;
import com.ecommerce.ecommerce.entities.users.repository.UserRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServices {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Order create(OrderInsertedDTO orderDTO) {
		var order = new Order();
		BeanUtils.copyProperties(orderDTO, order);

		User user = userRepository.findById(orderDTO.userId())
				.orElseThrow(() -> new NotFoundCustomException("Costumer not found"));
		order.setUser(user);

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
		User user = userRepository.findById(orderDTO.userId())
				.orElseThrow(() -> new NotFoundCustomException("Costumer not found"));
		order.setUser(user);

		return orderRepository.save(order);
	}

	public List<OrderDTO> findAll() {
		List<Order> orders = orderRepository.findAll();
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for (Order order : orders) {
			OrderDTO orderDTO = convertModelToOrderDTO(order);
			orderDTOs.add(orderDTO);
		}
		return orderDTOs;
	}

	public Page<OrderDTO> findAllByPage(Pageable pageable) {
		Page<Order> pages = orderRepository.findAll(pageable);
		List<OrderDTO> orderDTOs = pages.getContent().stream().map(this::convertModelToOrderDTO).collect(Collectors.toList());
		return new PageImpl<>(orderDTOs, pageable, pages.getTotalElements());
	}

	public OrderDTO findById(String id) {
		validateOrderNotExists(id);

		Optional<Order> orderModelOptional = orderRepository.findById(id);
		Order order = orderModelOptional.get();
		return convertModelToOrderDTO(order);
	}

	private OrderDTO convertModelToOrderDTO(Order order) {
		var orderDTO = new OrderDTO(
				Optional.ofNullable(order.getId()),
				order.getUser().getId(),
				order.getMethodPayment(),
				order.getTotalPrice(),
				Optional.ofNullable(order.getOrderItems())
		);

		return orderDTO;
	}

	private void validateOrderNotExists(String id) {
		Optional<Order> orderFound = orderRepository.findById(id);
		if (orderFound.isEmpty()) {
			throw new NotFoundCustomException("Order not found with id: " + id);
		}
	}
}
