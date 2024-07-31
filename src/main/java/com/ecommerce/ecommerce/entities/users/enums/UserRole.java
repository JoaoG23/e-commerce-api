package com.ecommerce.ecommerce.entities.users.enums;
public enum UserRole
{
	EMPLOYEE("employee"), COSTUMER("costumer"), ADMIN("admin");
	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}