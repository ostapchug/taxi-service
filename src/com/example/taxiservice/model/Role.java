package com.example.taxiservice.model;

/**
 * Role entity.
 * 
 */
public enum Role {
	ADMIN, CLIENT;
	
	public static Role getRole(Person person) {
		int roleId = person.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}

}
