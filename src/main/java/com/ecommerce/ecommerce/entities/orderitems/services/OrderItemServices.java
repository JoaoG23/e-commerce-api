package com.ecommerce.ecommerce.entities.orderitems.services;

import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemDTO;
import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemInsertedDTO;
import com.ecommerce.ecommerce.entities.orderitems.dtos.ItemQueryDTO;
import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orderitems.repository.OrderItemsRepository;
import com.ecommerce.ecommerce.entities.orders.repository.OrderRepository;
import com.ecommerce.ecommerce.entities.products.model.Product;
import com.ecommerce.ecommerce.entities.products.repository.ProductRepository;
import com.ecommerce.ecommerce.entities.stock.model.Stock;
import com.ecommerce.ecommerce.entities.stock.repository.StockRepository;
import com.ecommerce.ecommerce.infra.HandlerErros.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
	@Autowired
	private StockRepository stockRepository;

	@Transactional
	public OrderItem create(ItemInsertedDTO dto) {
		var entity = new OrderItem();
		entity.setOrder(orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")));
		entity.setProduct(productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));


		Boolean isExistsProductInOrder = orderItemRepository.existsByOrderAndProduct(
				orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")),
				productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found"))
		);

		if (isExistsProductInOrder == true) throw new NotFoundCustomException("Product already exists in this order");
		entity.setQuantity(dto.getQuantity());

		checkStockQuantity(dto);

		return orderItemRepository.save(entity);
	}

	@Transactional
	public OrderItem updateById(String id, ItemInsertedDTO dto) {
		validateIfOrderItemNotExistsById(id);
		var entity = new OrderItem();

		entity.setId(id);
		entity.setOrder(orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")));
		entity.setProduct(productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found")));
		entity.setQuantity(dto.getQuantity());

		Boolean isExistsProductInOrder = orderItemRepository.existsByIdAndOrderAndProduct(
				id,
				orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundCustomException("Order not found")),
				productRepository.findById(dto.getProductId()).orElseThrow(() -> new NotFoundCustomException("Product not found"))
		);

		if (isExistsProductInOrder == false)
			throw new NotFoundCustomException("Product already exists in another item");

		checkStockQuantity(dto);
		return orderItemRepository.save(entity);
	}

	public ItemQueryDTO findById(String id) {
		List<Object[]> orderModelList = (List<Object[]>) orderItemRepository.findOrderItemsById(id);
		List<ItemQueryDTO> orderItemDTOs = orderModelList.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return orderItemDTOs.get(0);
	}

	public List<ItemQueryDTO> findAll() {
		List<Object[]> items = (List<Object[]>) orderItemRepository.findAllOrderItems();
		List<ItemQueryDTO> itemsDto = items.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return itemsDto;
	}

	public List<ItemQueryDTO> findAllByOrderId(String orderId) {
		List<Object[]> items = (List<Object[]>) orderItemRepository.findByOrderId(orderId);
		List<ItemQueryDTO> itemsDto = items.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return itemsDto;
	}

	@Transactional
	public void deleteById(String id) {
		validateIfOrderItemNotExistsById(id);
		orderItemRepository.deleteById(id);
	}

	private ItemDTO convertToDto(OrderItem entity) {
		var dto = new ItemDTO();
		dto.setId(Optional.ofNullable(entity.getId()));
		dto.setQuantity(entity.getQuantity());
		return dto;
	}

	private ItemQueryDTO convertToOrderItemDTO(Object[] array) {
		return new ItemQueryDTO(
				(String) array[0],
				(String) array[1],
				(String) array[2],
				(String) array[3],
				(Integer) array[4],
				(String) array[5],
				(String) array[6],
				(BigDecimal) array[7],
				(BigDecimal) array[8]
		);
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

	private void validateIfOrderItemNotExistsById(String id) {
		Optional<OrderItem> orderItemFound = orderItemRepository.findById(id);
		if (orderItemFound.isEmpty()) {
			throw new NotFoundCustomException("item not found with id: " + id);
		}
	}
}
