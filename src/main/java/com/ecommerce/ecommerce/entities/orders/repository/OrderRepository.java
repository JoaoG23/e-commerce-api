package com.ecommerce.ecommerce.entities.orders.repository;

import com.ecommerce.ecommerce.entities.orders.model.Order;
import com.ecommerce.ecommerce.entities.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query(value = """
			SELECT
			  	items.order_id,
			    orders.method_payment,
			    orders.total_price,
			    orders.updated_at,
			    orders.user_id,
			    items.id,
			    items.quantity,
			    items.product_id,
			    product.details,
			    product."name" AS name_product,
			    product.price,
			    items.quantity * product.price AS total_price_item
			FROM public.order_items items
			lEFT JOIN public.orders orders ON items.order_id = orders.id
			LEFT JOIN public.products product ON items.product_id = product.id
			WHERE orders.id = :order_id
			""", nativeQuery = true)
	List<Object[]> findOrderItemsByOrderId(String order_id);



	@Query(value = """
			SELECT
			  	items.order_id,
			    orders.method_payment,
			    orders.total_price,
			    orders.updated_at,
			    orders.user_id,
			    items.id,
			    items.quantity,
			    items.product_id,
			    product.details,
			    product."name" AS name_product,
			    product.price,
			    items.quantity * product.price AS total_price_item
			FROM public.order_items items
			lEFT JOIN public.orders orders ON items.order_id = orders.id
			LEFT JOIN public.products product ON items.product_id = product.id
			""", nativeQuery = true)
	List<Object[]> findAllOrderItems();

}
