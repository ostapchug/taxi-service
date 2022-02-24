package com.example.taxiservice.model;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 */

public abstract class Entity implements Serializable{

	private static final long serialVersionUID = 5218535842751591153L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
