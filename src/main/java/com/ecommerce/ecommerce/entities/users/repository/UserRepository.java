package com.ecommerce.ecommerce.entities.users.repository;


import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import com.ecommerce.ecommerce.entities.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);

}
