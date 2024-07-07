package com.ecommerce.ecommerce.entities.users.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private Long id;

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	@Column(name = "full_name")
	private String fullname;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of( new SimpleGrantedAuthority("ROLE_USER"));
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
