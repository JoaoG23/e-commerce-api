package com.ecommerce.ecommerce.entities.orders.repository;

import com.ecommerce.ecommerce.entities.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
