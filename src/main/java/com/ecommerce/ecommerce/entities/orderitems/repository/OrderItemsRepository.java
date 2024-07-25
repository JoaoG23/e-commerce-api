package com.ecommerce.ecommerce.entities.orderitems.repository;

import com.ecommerce.ecommerce.entities.orderitems.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, String> {

}
