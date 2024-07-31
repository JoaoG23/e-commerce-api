package com.ecommerce.ecommerce.entities.users.dtos;

import java.util.Optional;

public record UserDTO(Optional<String> id, String name, String email, String password) {
}

