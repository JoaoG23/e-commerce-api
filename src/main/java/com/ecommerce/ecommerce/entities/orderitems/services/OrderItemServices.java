package com.ecommerce.ecommerce.entities.orderitems.services;

import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemDTO;
import com.ecommerce.ecommerce.entities.orderitems.dtos.OrderItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.repository.OrderItemsRepository;
import com.ecommerce.ecommerce.entities.orders.dtos.OrderDTO;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServices {
	@Autowired
	private OrderItemsRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public OrderItem create(OrderItemInsertedDTO dto) {
		var entity = new OrderItem();
		entity.setOrder(orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")));
		entity.setProduct(productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));

		entity.setQuantity(dto.getQuantity());
		return orderItemRepository.save(entity);
	}

	@Transactional
	public OrderItem updateById(String id, OrderItemInsertedDTO dto) {
		validateIfOrderItemNotExistsById(id);
		var entity = new OrderItem();

		entity.setId(id);
		entity.setOrder(orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")));
		entity.setProduct(productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));
		entity.setQuantity(dto.getQuantity());

		return orderItemRepository.save(entity);
	}




	public OrderDTO findById(String id) {
		List<Object[]> orderModelList = (List<Object[]>) orderItemRepository.findOrderItemsById(id);
		List<OrderDTO> orderItemDTOs = orderModelList.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return orderItemDTOs.get(0);
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

	@Transactional
	public void deleteById(String id) {
		validateIfOrderItemNotExistsById(id);
		orderItemRepository.deleteById(id);
	}

	private OrderItemDTO convertToDto(OrderItem entity) {
		var dto = new OrderItemDTO();
		dto.setId(Optional.ofNullable(entity.getId()));
		dto.setQuantity(entity.getQuantity());
		return dto;
	}

	private void validateIfOrderItemNotExistsById(String id) {
		Optional<OrderItem> orderItemFound = orderItemRepository.findById(id);
		if (orderItemFound.isEmpty()) {
			throw new NotFoundCustomException("item not found with id: " + id);
		}
	}
}
