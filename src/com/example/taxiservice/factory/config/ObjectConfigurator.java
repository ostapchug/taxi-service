package com.example.taxiservice.factory.config;

import com.example.taxiservice.factory.AppContext;
/**
 * ObjectConfigurator - allows to configure objects by annotated fields.
 */
public interface ObjectConfigurator {
	
	/**
	 * Configures given object.
	 * 
	 * @param Object - object to configure.
	 * 
	 * @param AppContext - context object with required resources for configuration.
	 */
	void configure(Object t, AppContext context);
}
