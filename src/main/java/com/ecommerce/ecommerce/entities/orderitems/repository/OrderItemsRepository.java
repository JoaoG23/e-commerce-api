package com.ecommerce.ecommerce.entities.orderitems.repository;

import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, String> {

	@Query(value = """
			SELECT
			    items.id,
			  	items.order_id,
			    orders.method_payment,
			    orders.user_id,
			    items.quantity,
			    items.product_id,
			    product.name_product,
			    product.price,
			    items.quantity * product.price AS total_price_item
			FROM public.order_items items
			lEFT JOIN public.orders orders ON items.order_id = orders.id
			LEFT JOIN public.products product ON items.product_id = product.id
			WHERE orders.id = :order_id
			""", nativeQuery = true)
	List<Object[]> findByOrderId(String order_id);
	@Query(value = """
			SELECT
			    items.id,
			  	items.order_id,
			    orders.method_payment,
			    orders.user_id,
			    items.quantity,
			    items.product_id,
			    product.name_product,
			    product.price,
			    items.quantity * product.price AS total_price_item
			FROM public.order_items items
			lEFT JOIN public.orders orders ON items.order_id = orders.id
			LEFT JOIN public.products product ON items.product_id = product.id
			WHERE items.id = :id
			""", nativeQuery = true)
	List<Object[]> findOrderItemsById(String id);

	@Query(value = """
			SELECT
			    items.id,
			  	items.order_id,
			    orders.method_payment,
			    orders.user_id,
			    items.quantity,
			    items.product_id,
			    product.name_product,
			    product.price,
			    items.quantity * product.price AS total_price_item
			FROM public.order_items items
			lEFT JOIN public.orders orders ON items.order_id = orders.id
			LEFT JOIN public.products product ON items.product_id = product.id
			""", nativeQuery = true)
	List<Object[]> findAllOrderItems();

	Boolean existsByOrderAndProduct(Order orderNotFound, Product productNotFound);

	Boolean existsByIdAndOrderAndProduct(String id,Order orderNotFound, Product productNotFound);
}
