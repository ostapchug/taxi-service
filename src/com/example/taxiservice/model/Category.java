package com.example.taxiservice.model;

import java.math.BigDecimal;

/**
 * Category entity.
 */
public class Category extends Entity {
	
	private static final long serialVersionUID = 2890878567526106371L;
	
	private String name;
	private BigDecimal price;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Category [name=" + name + ", price=" + price + "]";
	}	

}
