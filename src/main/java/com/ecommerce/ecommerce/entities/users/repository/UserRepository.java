package com.ecommerce.ecommerce.entities.users.repository;


import com.ecommerce.ecommerce.entities.users.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
	Optional<UserModel> findByEmail(String email);
}
