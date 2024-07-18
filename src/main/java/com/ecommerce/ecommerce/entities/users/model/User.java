package com.ecommerce.ecommerce.entities.users.model;


import com.ecommerce.ecommerce.entities.users.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.EMPLOYEE) {
			return List.of(
					new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
					new SimpleGrantedAuthority("ROLE_USER")
			);
		}
		if (this.role == UserRole.COSTUMER) {
			return List.of(
					new SimpleGrantedAuthority("ROLE_COSTUMER"),
					new SimpleGrantedAuthority("ROLE_USER")
			);
		}
		return List.of(
				new SimpleGrantedAuthority("ROLE_USER")
		);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
