package com.example.taxiservice.factory.config;

import org.reflections.Reflections;

/**
 * Config - allows to scan given package for available classes and retrieve available implementations.
 */
public interface Config {
	
	/**
	 * Returns interface implementation.
	 * 
	 * @param Class<T> - interface.
	 * 
	 * @return Class<? extends T> - implementation of the given interface.
	 */
	<T> Class<? extends T> getImplClass(Class<T> ifc);
	
	Reflections getScanner();
}
