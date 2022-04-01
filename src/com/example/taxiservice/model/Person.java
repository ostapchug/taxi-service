package com.example.taxiservice.model;

/**
 * Data access object for Person entity
 */
public class Person extends Entity {

	private static final long serialVersionUID = -6036183794948697678L;
	
	private String phone;
	private String password;
	private String name;
	private String surname;
	private int roleId;
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public String toString() {
		return "Person [phone=" + phone + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", roleId=" + roleId + "]";
	}	

}
